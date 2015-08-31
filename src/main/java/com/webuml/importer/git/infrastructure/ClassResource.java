package com.webuml.importer.git.infrastructure;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ClassResource extends com.webuml.importer.domain.metamodel.Class {

  @JsonIgnore
  String _links;

  public ClassResource() {
  }

  public String get_links() {
    return _links;
  }

  public void set_links(String _links) {
    this._links = _links;
  }
}
