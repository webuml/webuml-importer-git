package com.webuml.importer.projectmanagerclient;

import com.webuml.importer.domain.primitives.ElementId;
import com.webuml.importer.domain.primitives.ProjectId;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ProjectManagerHypermediaDocumentImplLearningTest {

  private ProjectManagerHypermediaDocumentImpl projectManagerRestClient;

  @BeforeMethod
  public void setUp() throws Exception {
    projectManagerRestClient = new ProjectManagerHypermediaDocumentImpl();
    projectManagerRestClient.setProjectManagerUrl("http://localhost:8082");
  }

  @Test(enabled = false)
  public void sample_usage_how_to_create_an_association_via_POST_request() {
    ElementId id = projectManagerRestClient.createAssociationId(new ProjectId("4711"));
    assertThat(id).isNotNull();
    assertThat(id.getValue()).isNotEmpty();
  }

}