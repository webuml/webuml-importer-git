package com.webuml.importer.git.repository;

import com.webuml.importer.domain.primitives.ProjectId;
import com.webuml.importer.git.controller.ImportProcessStatus;
import com.webuml.importer.git.importprocess.UserPreferences;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "GitProject")
public class GitProject extends AbstractDocument {

  private String name;
  private String description;
  private String url;
  private ImportProcessStatus status;
  private ProjectId projectId;
  private UserPreferences userPreferences;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public ImportProcessStatus getStatus() {
    return status;
  }

  public void setStatus(ImportProcessStatus status) {
    this.status = status;
  }

  public ProjectId getProjectId() {
    return projectId;
  }

  public void setProjectId(ProjectId projectId) {
    this.projectId = projectId;
  }

  public UserPreferences getUserPreferences() {
    return userPreferences;
  }

  public void setUserPreferences(UserPreferences userPreferences) {
    this.userPreferences = userPreferences;
  }
}