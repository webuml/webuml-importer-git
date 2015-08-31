package com.webuml.importer.projectmanagerclient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.annotation.Nullable;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
class AnyElementOfProject implements Serializable {

  private String id;
  private String projectId;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Nullable
  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }
}
