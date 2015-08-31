package com.webuml.importer.domain.metamodel;

import com.webuml.importer.domain.primitives.PropertyId;

import java.util.Set;

public interface OwnedAttributeHolder {

  Set<PropertyId> getOwnedAttribute();

  void setOwnedAttribute(Set<PropertyId> ownedAttributes);
}
