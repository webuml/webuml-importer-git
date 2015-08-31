package com.webuml.importer.domain.metamodel;

import com.webuml.importer.domain.primitives.ElementId;
import com.webuml.importer.domain.primitives.Entity;
import com.webuml.importer.domain.primitives.PropertyId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "properties")
public class Property extends Entity<PropertyId> implements TypedElement, NamedElement, Element<PropertyId> {

  String name;

  ElementId typeId;
  String defaultValue;

  Boolean isReadOnly;

  public Property() {
    super(PropertyId.class);
  }

  public Property(PropertyId id) {
    super(id);
  }

  @Override
  public String toString() {
    return name;
  }

  public Boolean getIsReadOnly() {
    return isReadOnly;
  }

  public void setIsReadOnly(Boolean isReadOnly) {
    this.isReadOnly = isReadOnly;
  }

  public String getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  public ElementId getTypeId() {
    return typeId;
  }

  public void setTypeId(ElementId typeId) {
    this.typeId = typeId;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }
}
