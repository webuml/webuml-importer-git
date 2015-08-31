package com.webuml.importer.domain.metamodel;

import com.webuml.importer.domain.primitives.ElementId;

public interface TypedElement {

  ElementId getTypeId();

  void setTypeId(ElementId typeId);
}
