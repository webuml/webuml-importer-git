package com.webuml.importer.domain.metamodel;

import com.webuml.importer.domain.primitives.PropertyId;

import java.util.Set;

public interface MemberEndHolder {

  Set<PropertyId> getMemberEnd();

  void setMemberEnd(Set<PropertyId> ownedEnds);
}
