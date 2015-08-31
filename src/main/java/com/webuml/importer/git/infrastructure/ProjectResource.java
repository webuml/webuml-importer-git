package com.webuml.importer.git.infrastructure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webuml.importer.domain.projectmodel.Project;

public class ProjectResource extends Project {

  @JsonIgnore
  String _links;

  public ProjectResource() {
  }

  public String get_links() {
    return _links;
  }

  public void set_links(String _links) {
    this._links = _links;
  }
}
