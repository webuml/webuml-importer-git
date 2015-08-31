package com.webuml.importer.git.infrastructure;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PackageResource extends com.webuml.importer.domain.metamodel.Package {

  @JsonIgnore
  String _links;

  public PackageResource() {
  }

  public String get_links() {
    return _links;
  }

  public void set_links(String _links) {
    this._links = _links;
  }
}
