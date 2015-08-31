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
import com.webuml.importer.parser.candidate.*;
import com.webuml.importer.projectmanagerclient.ProjectManagerHypermediaDocument;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Provider;
import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class ElementModelCreatorTest {

  protected static final ProjectId PROJECT_ID = new ProjectId();
  protected ElementModelCreator elementModelCreator;
  protected CandidateModel candidateModel;
  protected ProjectManagerHypermediaDocument projectManagerHypermediaDocument;
  private URI url = URI.create("http://github.com/libgit2/libgit2/archive/development.zip");

  @BeforeMethod
  public void setUp() throws Exception {
    elementModelCreator = new ElementModelCreator();
    candidateModel = new CandidateModel();


    Provider providerMock = mock(Provider.class);
    projectManagerHypermediaDocument = mock(ProjectManagerHypermediaDocument.class);
    when(providerMock.get()).thenReturn(new MyProjectManagerHypermediaDocumentMock());
    elementModelCreator.setProjectManagerHypermediaDocument(providerMock);
  }

  @Test
  public void nothing() throws Exception {
    BackEndModelInstance elementModel = elementModelCreator.createBackEndModelInstance(candidateModel, PROJECT_ID, url);
    assertNotNull(elementModel);
    assertNotNull(elementModel.getClasses());
    assertNotNull(elementModel.getPackages());
    assertNotNull(elementModel.getAssociations());
    assertNotNull(elementModel.getProperties());
    assertNotNull(elementModel.getProject());
    assertEquals(elementModel.getProject().getName(), url.toString());
  }

  @Test
  public void singleClass() throws Exception {
    newCandidate("ClassName", "com.webuml.model");

    BackEndModelInstance backEndModelInstance = elementModelCreator.createBackEndModelInstance(candidateModel, PROJECT_ID, url);
    assertNotNull(backEndModelInstance);
    assertNotNull(backEndModelInstance.getClasses());
    assertEquals(backEndModelInstance.getClasses().size(), 1);
    assertEquals(backEndModelInstance.getClasses().get(0).getName(), "ClassName");
    PackageId packageId = backEndModelInstance.getClasses().get(0).getOwningPackage();
    assertNotNull(packageId);
    Package aPackage = findPackage(backEndModelInstance, packageId);
    assertNotNull(aPackage);
    assertEquals(aPackage.getName(), "com.webuml.model");
    assertNotNull(backEndModelInstance.getPackages());
    assertEquals(backEndModelInstance.getPackages().size(), 1);
    assertEquals(backEndModelInstance.getPackages().get(0), aPackage);
    assertEquals(backEndModelInstance.getAssociations().size(), 0);
  }

  @Test
  public void twoClassesTwoPackages() throws Exception {
    newCandidate("ClassName1", "com.webuml.model1");
    newCandidate("ClassName2", "com.webuml.model2");

    BackEndModelInstance backEndModelInstance = elementModelCreator.createBackEndModelInstance(candidateModel, PROJECT_ID, url);

    assertEquals(backEndModelInstance.getClasses().size(), 2);

    Class classElement1 = backEndModelInstance.getClasses().get(0);
    assertEquals(classElement1.getName(), "ClassName1");
    Package aPackage = findPackage(backEndModelInstance, classElement1.getOwningPackage());
    assertNotNull(aPackage);
    assertEquals(aPackage.getName(), "com.webuml.model1");

    Class classElement2 = backEndModelInstance.getClasses().get(1);
    assertEquals(classElement2.getName(), "ClassName2");
    aPackage = findPackage(backEndModelInstance, classElement2.getOwningPackage());
    assertNotNull(aPackage);
    assertEquals(aPackage.getName(), "com.webuml.model2");

    assertNotNull(backEndModelInstance.getPackages());
    assertEquals(backEndModelInstance.getPackages().size(), 2);
    assertEquals(backEndModelInstance.getPackages().get(0).getName(), "com.webuml.model1");
    assertEquals(backEndModelInstance.getPackages().get(1).getName(), "com.webuml.model2");
  }

  @Test
  public void unidirectionalAssociation() throws Exception {
    ClassDeclarationCandidate className1 = newCandidate("ClassName1", "com.webuml.model1");
    ClassDeclarationCandidate className2 = newCandidate("ClassName2", "com.webuml.model2");

    ClassRelationCandidate classRelationCandidate = new ClassRelationCandidate();
    classRelationCandidate.setToClassName(className2.getClassName());
    classRelationCandidate.setToPackageName(className2.getPackageName());
    classRelationCandidate.setPackageResolveState(PackageResolveState.RESOLVED);
    className1.getClassRelationCandidates().add(classRelationCandidate);

    BackEndModelInstance backEndModelInstance = elementModelCreator.createBackEndModelInstance(candidateModel, PROJECT_ID, url);

    assertEquals(backEndModelInstance.getClasses().size(), 2);
    Class classElement1 = backEndModelInstance.getClasses().get(0);
    assertEquals(classElement1.getName(), className1.getClassName());

    Class classElement2 = backEndModelInstance.getClasses().get(1);
    assertEquals(classElement2.getName(), className2.getClassName());
    assertEquals(backEndModelInstance.getAssociations().size(), 1);
    assertEquals(backEndModelInstance.getProperties().size(), 2);

    Association association = backEndModelInstance.getAssociations().stream().findFirst().get();

    assertEquals(association.getOwnedEnd().size(), 1);
    assertEquals(association.getMemberEnd().size(), 2);
  }

  @Test
  public void relationToClassNotInScope() throws Exception {
    ClassDeclarationCandidate classDeclarationCandidate = newCandidate("ClassName1", "com.webuml.model1");
    createRelation(classDeclarationCandidate, "ClassName2", "org.spring", PackageResolveState.RESOLVED, "attributeName");

    BackEndModelInstance backEndModelInstance = elementModelCreator.createBackEndModelInstance(candidateModel, PROJECT_ID, url);
    assertNotNull(backEndModelInstance);
    assertNotNull(backEndModelInstance.getClasses());
    assertEquals(backEndModelInstance.getClasses().size(), 1);
    Class classElement1 = backEndModelInstance.getClasses().get(0);
    assertEquals(classElement1.getOwnedAttribute().size(), 1);
  }

  @Test
  public void relationToClassInScope() throws Exception {
    ClassDeclarationCandidate classDeclarationCandidate = newCandidate("ClassName1", "com.webuml.model");
    newCandidate("ClassName2", "com.webuml.model");
    createRelation(classDeclarationCandidate, "ClassName2", "com.webuml.model", PackageResolveState.RESOLVED, "attributeName");

    BackEndModelInstance backEndModelInstance = elementModelCreator.createBackEndModelInstance(candidateModel, PROJECT_ID, url);

    assertEquals(backEndModelInstance.getClasses().size(), 2);
    Class classElement1 = backEndModelInstance.getClasses().get(0);
    Class classElement2 = backEndModelInstance.getClasses().get(1);
    Set<PropertyId> ownedAttribute = classElement1.getOwnedAttribute();
    assertEquals(ownedAttribute.size(), 1);
    Property property = findProperty(ownedAttribute.iterator().next(), backEndModelInstance);
    assertEquals(property.getTypeId(), classElement2.getId());
    assertEquals(property.getName(), "attributeName");
    assertEquals(backEndModelInstance.getAssociations().size(), 1);
    Association association = backEndModelInstance.getAssociations().iterator().next();
    assertThat(association.getMemberEnd()).contains(property.getId());
    assertThat(association.getOwnedEnd()).excludes(property.getId());
    assertEquals(association.getMemberEnd().size(), 2);
  }

  @Test
  public void relationToClassInScopeWildCardImport() throws Exception {
    ClassDeclarationCandidate classCandidate1 = newCandidate("ClassName1", "com.webuml.model");
    newCandidate("ClassName2", "com.webuml.model.to");
    ClassRelationCandidate relation = createRelation(classCandidate1, "ClassName2", null, PackageResolveState.WILDCARD, "attributeName");
    relation.getPossibleToPackageNames().add("com.webuml.model.to");

    BackEndModelInstance backEndModelInstance = elementModelCreator.createBackEndModelInstance(candidateModel, PROJECT_ID, url);

    assertEquals(backEndModelInstance.getClasses().size(), 2);
    Class clazz1 = backEndModelInstance.getClasses().get(0);
    assertEquals(clazz1.getName(), "ClassName1");
    com.webuml.importer.domain.metamodel.Class clazz2 = backEndModelInstance.getClasses().get(1);
    assertEquals(clazz2.getName(), "ClassName2");
    assertEquals(clazz1.getOwnedAttribute().size(), 1);
    Set<PropertyId> ownedAttribute = clazz1.getOwnedAttribute();
    assertEquals(ownedAttribute.size(), 1);
    Property property = findProperty(ownedAttribute.iterator().next(), backEndModelInstance);
    assertEquals(property.getTypeId(), clazz2.getId());
  }

  protected ClassRelationCandidate createRelation(ClassDeclarationCandidate classDeclarationCandidate, String className2, String packageName, PackageResolveState resolved, String attributeName) {
    ClassRelationCandidate classRelationCandidate = new ClassRelationCandidate();
    classRelationCandidate.setToClassName(className2);
    classRelationCandidate.setAttributeName(attributeName);
    classRelationCandidate.setToPackageName(packageName);
    classRelationCandidate.setPackageResolveState(resolved);
    classRelationCandidate.setRelationType(ClassCandidateRelationType.MEMBER);
    classDeclarationCandidate.getClassRelationCandidates().add(classRelationCandidate);
    return classRelationCandidate;
  }

  protected ClassDeclarationCandidate newCandidate(String className, String packageName) {
    ClassDeclarationCandidate classDeclarationCandidate = new ClassDeclarationCandidate();
    classDeclarationCandidate.setClassName(className);
    classDeclarationCandidate.setPackageName(packageName);
    candidateModel.getClassDeclarationCandidates().add(classDeclarationCandidate);
    return classDeclarationCandidate;
  }

  protected Package findPackage(BackEndModelInstance elementModel, PackageId packageId) {
    List<Package> packages = elementModel.getPackages();
    for (Package aPackage : packages) {
      if (aPackage.getId().getValue().equals(packageId.getValue())) {
        return aPackage;
      }
    }
    return null;
  }

  protected Property findProperty(PropertyId next, BackEndModelInstance backEndModelInstance) {
    return backEndModelInstance.getProperties().stream().filter(property -> property.getId() == next).findFirst().get();
  }

  protected static class MyProjectManagerHypermediaDocumentMock implements ProjectManagerHypermediaDocument {

    @Override
    public ElementId createAssociationId(ProjectId projectId) {
      return new ElementId(UUID.randomUUID().toString());
    }

    @Override
    public PackageId createPackageId(ProjectId projectId) {
      return new PackageId(UUID.randomUUID().toString());
    }

    @Override
    public ProjectId createProjectId() {
      return new ProjectId(UUID.randomUUID().toString());
    }

    @Override
    public PropertyId createPropertyId() {
            return new PropertyId(UUID.randomUUID().toString());
    }

    @Override
    public ElementId createClassId(ProjectId projectId) {
      return new ElementId(UUID.randomUUID().toString());
    }
  }
}
