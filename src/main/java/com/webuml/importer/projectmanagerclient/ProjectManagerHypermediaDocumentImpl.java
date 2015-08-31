package com.webuml.importer.projectmanagerclient;

import com.damnhandy.uri.template.MalformedUriTemplateException;
import com.damnhandy.uri.template.UriTemplate;
import com.damnhandy.uri.template.VariableExpansionException;
import com.theoryinpractise.halbuilder.api.Link;
import com.theoryinpractise.halbuilder.api.ReadableRepresentation;
import com.webuml.importer.domain.primitives.ElementId;
import com.webuml.importer.domain.primitives.PackageId;
import com.webuml.importer.domain.primitives.ProjectId;
import com.webuml.importer.domain.primitives.PropertyId;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Scope(SCOPE_PROTOTYPE)
@Component
public class ProjectManagerHypermediaDocumentImpl implements ProjectManagerHypermediaDocument {

  private final Logger logger = LoggerFactory.getLogger(ProjectManagerHypermediaDocumentImpl.class);

  private HyperMediaServiceDocument serviceDocument;

  @Value("${projectManager.url}")
  void setProjectManagerUrl(String projectManagerUrl) {
    serviceDocument = new HyperMediaServiceDocument(projectManagerUrl);
  }

  public ProjectId createProjectId() {
    String uri = expandLinkFor("http://projects.webuml.com/rel/projects");
    String id = postRequestAndFetchElementId(uri);
    return new ProjectId(id);
  }

  @Override
  public PropertyId createPropertyId() {
    String uri = expandLinkFor("http://projects.webuml.com/rel/properties");
    String id = postRequestAndFetchElementId(uri);
    return new PropertyId(id);
  }

  @Override
  public PackageId createClassId(ProjectId projectId) {
    String uri = expandLinkFor("http://projects.webuml.com/rel/classes", projectId);
    String id = postRequestAndFetchElementId(uri);
    return new PackageId(id);
  }

  @Override
  public ElementId createAssociationId(ProjectId projectId) {
    String uri = expandLinkFor("http://projects.webuml.com/rel/associations", projectId);
    String id = postRequestAndFetchElementId(uri);
    return new ElementId(id);
  }

  @Override
  public PackageId createPackageId(ProjectId projectId) {
    String uri = expandLinkFor("http://projects.webuml.com/rel/packages", projectId);
    String id = postRequestAndFetchElementId(uri);
    return new PackageId(id);
  }

  private String expandLinkFor(String relation) {
    return expandLinkFor(relation, null);
  }

  private String expandLinkFor(String relation, @Nullable ProjectId projectId) {
    ReadableRepresentation representation = serviceDocument.fetch();
    Link packages = representation.getLinkByRel(relation);
    assert packages.hasTemplate();
    String uri;
    try {
      UriTemplate uriTemplate = UriTemplate.fromTemplate(packages.getHref());
      if (projectId != null) {
        uriTemplate = uriTemplate.set("projectId", projectId.getValue());
      }
      uri = uriTemplate.expand();
      logger.info("expanded link for rel=" + relation + ", link=" + uri);
    } catch (MalformedUriTemplateException | VariableExpansionException e) {
      throw new RuntimeException(e);
    }
    return uri;
  }

  private String postRequestAndFetchElementId(String uri) {
    AnyElementOfProject response = JerseyClientBuilder.newClient()
        .register(JacksonFeature.class)
        .target(uri)
        .request(APPLICATION_JSON_TYPE)
        .buildPost(null)
        .invoke(AnyElementOfProject.class);
    assert response.getId() != null;
    return response.getId();
  }

}
