package com.webuml.importer.git.importprocess;

import com.webuml.importer.Application;
import com.webuml.importer.domain.BackEndModelInstance;
import com.webuml.importer.domain.metamodel.Association;
import com.webuml.importer.domain.metamodel.Class;
import com.webuml.importer.domain.metamodel.Package;
import com.webuml.importer.domain.metamodel.Property;
import com.webuml.importer.domain.primitives.ElementId;
import com.webuml.importer.domain.primitives.ProjectId;
import com.webuml.importer.domain.projectmodel.Project;
import com.webuml.importer.projectmanagerclient.ProjectManagerHypermediaDocument;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest
@ActiveProfiles(profiles = "local")
public class RestMetaModelSenderIntegrationTest extends AbstractTestNGSpringContextTests {

  @Inject
  RestMetaModelSender restMetaModelSender;

  @Inject
  void setProjectManagerHypermediaDocument(Provider<ProjectManagerHypermediaDocument> projectManagerClientProvider) {
    projectManagerHypermediaDocument = projectManagerClientProvider.get();
  }

  private ProjectManagerHypermediaDocument projectManagerHypermediaDocument;
  private BackEndModelInstance backEndModelInstance;
  private Project project;
  private ArrayList<Package> packages;
  private ArrayList<Class> clazzes;
  private HashSet<Property> properties;
  private ProjectId projectId;
  private HashSet<Association> associations;

  @BeforeMethod
  public void setUp() throws Exception {
    backEndModelInstance = new BackEndModelInstance();
    projectId = projectManagerHypermediaDocument.createProjectId();
    project = new Project(projectId);
    project.setName("TestProject-" + new Date());
    backEndModelInstance.setProject(project);
    packages = new ArrayList<>();
    backEndModelInstance.setPackages(packages);
    clazzes = new ArrayList<>();
    backEndModelInstance.setClasses(clazzes);
    properties = new HashSet<>();
    backEndModelInstance.setProperties(properties);
    associations = new HashSet<>();
    backEndModelInstance.setAssociations(associations);
  }

  @Test(enabled = false)
  public void postingProjectTest() throws Exception {
    Package aPackage = createPackage("com.webuml");
    Class clazz = createClazz(aPackage);
    Property prop1 = createProperty("property1", clazz.getId());
    Property prop2 = createProperty("property2", clazz.getId());
    clazz.getOwnedAttribute().add(prop1.getId());
    Association association = createAssociation();
    association.getMemberEnd().add(prop1.getId());
    association.getMemberEnd().add(prop2.getId());
    association.getOwnedEnd().add(prop2.getId());
    restMetaModelSender.send(backEndModelInstance);
  }

  private Association createAssociation() {
    Association association = new Association();
    association.setName("nameOfAssociation");
    association.setProjectId(projectId);
    associations.add(association);
    return association;
  }

  private Package createPackage(String name) {
    Package aPackage = new Package();
    aPackage.setName(name);
    aPackage.setProjectId(projectId);
    packages.add(aPackage);
    return aPackage;
  }

  private Class createClazz(Package aPackage) {
    Class clazz = new com.webuml.importer.domain.metamodel.Class();
    clazz.setId(new ElementId());
    clazz.setName("ClazzName");
    clazz.setProjectId(projectId);
    clazz.setOwningPackage(aPackage.getId());
    clazzes.add(clazz);
    return clazz;
  }

  private Property createProperty(String propertyName, ElementId typeId) {
    Property property = new Property();
    property.setName(propertyName);
    property.setTypeId(typeId);
    properties.add(property);
    return property;
  }
}
