package com.webuml.importer.domain.metamodel;

import com.webuml.importer.domain.primitives.PropertyId;

import java.util.Set;

public interface OwnedEndHolder {

  Set<PropertyId> getOwnedEnd();

  void setOwnedEnd(Set<PropertyId> ownedEnds);
}
