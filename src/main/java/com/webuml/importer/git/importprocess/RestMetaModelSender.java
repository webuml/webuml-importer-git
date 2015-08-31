package com.webuml.importer.git.importprocess;

import com.damnhandy.uri.template.MalformedUriTemplateException;
import com.damnhandy.uri.template.UriTemplate;
import com.damnhandy.uri.template.VariableExpansionException;
import com.webuml.importer.domain.BackEndModelInstance;
import com.webuml.importer.domain.metamodel.Association;
import com.webuml.importer.domain.metamodel.Package;
import com.webuml.importer.domain.metamodel.Property;
import com.webuml.importer.domain.projectmodel.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestMetaModelSender {

  private Logger logger = LoggerFactory.getLogger(RestMetaModelSender.class);

  @Value("${projectManager.url}")
  String projectManagerUrl;

  public void send(BackEndModelInstance backEndModelInstance) {
    Project project = backEndModelInstance.getProject();
    putProject(project);
    backEndModelInstance.getClasses().forEach(this::putClass);
    backEndModelInstance.getProperties().forEach(this::postProperty);
    backEndModelInstance.getPackages().forEach(aPackage -> putPackages(aPackage));
    backEndModelInstance.getAssociations().forEach(association -> putAssociation(association));
  }

  private void putProject(Project project) {
    RestTemplate restTemplate = new RestTemplate();
    String uri = projectManagerUrl + "/projects/{projectId}";
    try {
      logger.info("putProject : " + UriTemplate.fromTemplate(uri).set("classId", project.getId()).set("projectId", project.getId()).expand());
    } catch (MalformedUriTemplateException | VariableExpansionException e) {
      throw new RuntimeException(e);
    }
    restTemplate.put(
        uri,
        project,
        project.getId().getValue()
    );
  }

  private void putClass(com.webuml.importer.domain.metamodel.Class clazz) {
    RestTemplate restTemplate = new RestTemplate();
    String uri = projectManagerUrl + "/classes/{classId}";
    try {
      logger.info("putClass : " + UriTemplate.fromTemplate(uri).set("classId", clazz.getId()).expand());
    } catch (MalformedUriTemplateException | VariableExpansionException e) {
      throw new RuntimeException(e);
    }
    restTemplate.put(
        uri,
        clazz,
        clazz.getId()
    );
  }

  private void postProperty(Property property) {
    RestTemplate projectService = new RestTemplate();
    String uri = projectManagerUrl + "/properties/{propertyId}";
    try {
      logger.info("postProperty : " + UriTemplate.fromTemplate(uri).set("propertyId", property.getId().toString()).expand());
    } catch (MalformedUriTemplateException | VariableExpansionException e) {
      throw new RuntimeException(e);
    }
    projectService.put(uri, property, property.getId()
    );
  }

  private void putPackages(Package aPackage) {
    RestTemplate projectService = new RestTemplate();
    String uri = projectManagerUrl + "/packages/{packageId}";
    logger.info("putPackage " + aPackage.getId() + " to " + uri);
    projectService.put(uri, aPackage, aPackage.getId());
  }

  private void putAssociation(Association association) {
    RestTemplate projectService = new RestTemplate();
    String uri = projectManagerUrl + "/associations/{associationId}";
    logger.info("put Association " + association.getId() + " to " + uri);
    projectService.put(uri, association, association.getId());
  }
}
