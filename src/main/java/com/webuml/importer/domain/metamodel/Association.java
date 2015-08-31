package com.webuml.importer.domain.metamodel;

import com.webuml.importer.domain.primitives.ElementId;
import com.webuml.importer.domain.primitives.PropertyId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "associations")
public class Association extends Classifier implements OwnedEndHolder, MemberEndHolder {

  Set<PropertyId> ownedEnd = new HashSet<>();
  Set<PropertyId> memberEnd = new HashSet<>();

  public Association() {
    super();
  }

  public Association(ElementId id) {
    super(id);
  }

  @Override
  public Set<PropertyId> getOwnedEnd() {
    return ownedEnd;
  }

  @Override
  public void setOwnedEnd(Set<PropertyId> ownedEnd) {
    this.ownedEnd = ownedEnd;
  }

  @Override
  public Set<PropertyId> getMemberEnd() {
    return memberEnd;
  }

  @Override
  public void setMemberEnd(Set<PropertyId> memberEnd) {
    this.memberEnd = memberEnd;
  }
}
