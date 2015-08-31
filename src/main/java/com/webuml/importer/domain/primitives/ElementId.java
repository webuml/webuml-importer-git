package com.webuml.importer.domain.primitives;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = CustomEntityJsonSerializer.class)
public class ElementId extends Identifier {

  public ElementId() {
    super();
  }

  public ElementId(String value) {
    super(value);
  }
}
