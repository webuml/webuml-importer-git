package com.webuml.importer.parser;

import com.webuml.importer.domain.BackEndModelInstance;
import com.webuml.importer.domain.metamodel.Association;
import com.webuml.importer.domain.metamodel.Class;
import com.webuml.importer.domain.metamodel.Package;
import com.webuml.importer.domain.metamodel.Property;
import com.webuml.importer.domain.primitives.ElementId;
import com.webuml.importer.domain.primitives.PackageId;
import com.webuml.importer.domain.primitives.ProjectId;
import com.webuml.importer.domain.primitives.PropertyId;
import com.webuml.importer.domain.projectmodel.Project;
import com.webuml.importer.parser.candidate.CandidateModel;
import com.webuml.importer.parser.candidate.ClassDeclarationCandidate;
import com.webuml.importer.parser.candidate.ClassRelationCandidate;
import com.webuml.importer.projectmanagerclient.ProjectManagerHypermediaDocument;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Provider;
import java.net.URI;
import java.util.*;

import static com.webuml.importer.parser.candidate.PackageResolveState.*;

@Component
public class ElementModelCreator {

  private ProjectManagerHypermediaDocument projectManagerHypermediaDocument;

  @Inject
  void setProjectManagerHypermediaDocument(Provider<ProjectManagerHypermediaDocument> projectManagerClientProvider) {
    projectManagerHypermediaDocument = projectManagerClientProvider.get();
  }

  public BackEndModelInstance createBackEndModelInstance(CandidateModel candidateModel, ProjectId projectId, URI url) {
    return new Stateful(candidateModel, projectId, url).createElementModel();
  }

  private class Stateful {

    private final CandidateModel candidateModel;
    private ProjectId projectId;
    private URI url;
    private List<Package> packages = new ArrayList<>();
    private List<Class> classElements = new ArrayList<>();
    private Set<Property> properties = new HashSet<>();
    private Project project;
    private HashSet<Association> associations = new HashSet<>();

    public Stateful(CandidateModel candidateModel, ProjectId projectId, URI url) {
      this.candidateModel = candidateModel;
      this.projectId = projectId;
      this.url = url;
    }

    public BackEndModelInstance createElementModel() {
      createProject();
      createClassElements();
      createClassRelations();

      BackEndModelInstance backEndModelInstance = new BackEndModelInstance();
      backEndModelInstance.setProject(project);
      backEndModelInstance.setClasses(classElements);
      backEndModelInstance.setPackages(packages);
      backEndModelInstance.setAssociations(associations);
      backEndModelInstance.setProperties(properties);
      return backEndModelInstance;
    }

    private void createProject() {
      project = new Project(projectId);
      project.setName(url.toString());
    }

    private void createClassRelations() {
      for (ClassDeclarationCandidate classDeclarationCandidate : candidateModel.getClassDeclarationCandidates()) {
        Class classElement = findClassElement(classDeclarationCandidate.getClassName(), classDeclarationCandidate.getPackageName());
        Class relationTo = null;
        for (ClassRelationCandidate classRelationCandidate : classDeclarationCandidate.getClassRelationCandidates()) {
          if (classRelationCandidate.getPackageResolveState() == RESOLVED) {
            relationTo = findClassElement(classRelationCandidate.getToClassName(), classRelationCandidate.getToPackageName());
          }
          if (classRelationCandidate.getPackageResolveState() == UNRESOLVED) {
            PackageId aPackage = classElement.getOwningPackage();
            relationTo = findClassElement(classRelationCandidate.getToClassName(), findPackage(aPackage));
          }
          if (classRelationCandidate.getPackageResolveState() == WILDCARD) {
            Iterator<String> iterator = classRelationCandidate.getPossibleToPackageNames().iterator();
            while (iterator.hasNext() && relationTo == null) {
              relationTo = findClassElement(classRelationCandidate.getToClassName(), iterator.next());
            }
          }

          createRelation(classElement, relationTo, classRelationCandidate);
        }
      }
    }

    private void createRelation(Class classElement, Class relationTo, ClassRelationCandidate classRelationCandidate) {
      Property ownProperty = createProperty(classRelationCandidate.getAttributeName());
      classElement.getOwnedAttribute().add(ownProperty.getId());

      if (relationTo != null) {
        ownProperty.setTypeId(relationTo.getId());

        Property otherProperty = createProperty("");
        otherProperty.setTypeId(classElement.getId());

        HashSet<PropertyId> ownedEnd = new HashSet<>();
        ownedEnd.add(otherProperty.getId());
        HashSet<PropertyId> memberEnd = new HashSet<>();
        memberEnd.add(otherProperty.getId());
        memberEnd.add(ownProperty.getId());

        Association association = createAssociation();
        association.setOwnedEnd(ownedEnd);
        association.setMemberEnd(memberEnd);
      }
    }

    private Association createAssociation() {
      ElementId associationId = projectManagerHypermediaDocument.createAssociationId(projectId);
      Association association = new Association(associationId);
      association.setProjectId(projectId);
      associations.add(association);
      return association;
    }

    private Property createProperty(String attributeName) {
      PropertyId propertyId = projectManagerHypermediaDocument.createPropertyId();
      Property property = new Property(propertyId);
      property.setName(attributeName);
      properties.add(property);
      return property;
    }

    private String findPackage(PackageId aPackage) {
      return packages.stream().filter(thePackage -> thePackage.getId() == aPackage).findFirst().get().getName();
    }

    private void createClassElements() {
      candidateModel.getClassDeclarationCandidates().forEach(this::createClazz);
    }

    private void createClazz(ClassDeclarationCandidate classDeclarationCandidate) {
      ElementId classId = projectManagerHypermediaDocument.createClassId(projectId);
      Class clazz = new Class(classId);
      clazz.setProjectId(project.getId());
      clazz.setName(classDeclarationCandidate.getClassName());
      Package aPackage = getOrCreate(classDeclarationCandidate.getPackageName());
      clazz.setOwningPackage(aPackage.getId());
      classElements.add(clazz);
    }

    private Package getOrCreate(String packageName) {
      for (Package aPackage : packages) {
        if (aPackage.getName().equals(packageName)) {
          return aPackage;
        }
      }
      return createPackage(packageName);
    }

    private Package createPackage(String packageName) {
      PackageId packageId = projectManagerHypermediaDocument.createPackageId(projectId);

      Package aPackage = new Package(packageId);
      aPackage.setName(packageName);
      aPackage.setProjectId(project.getId());
      packages.add(aPackage);
      return aPackage;
    }

    private Class findClassElement(String className, String packageName) {
      for (com.webuml.importer.domain.metamodel.Class classModel : classElements) {
        Package aPackage = getOrCreate(packageName);
        if (aPackage.getName().equals(packageName)
            && classModel.getName().equals(className)) {
          return classModel;
        }
      }
      return null;
    }
  }
}
