package com.webuml.importer.domain.metamodel;

import com.webuml.importer.domain.primitives.ElementId;
import com.webuml.importer.domain.primitives.PackageId;
import com.webuml.importer.domain.primitives.PropertyId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "classes")
public class Class extends Classifier implements OwnedAttributeHolder, PackageableElement {

  Set<PropertyId> ownedAttribute = new HashSet<>();

  PackageId owningPackage;

  public Class() {
    super();
  }

  public Class(ElementId id) {
    super(id);
  }

  @Override
  public Set<PropertyId> getOwnedAttribute() {
    return ownedAttribute;
  }

  @Override
  public void setOwnedAttribute(Set<PropertyId> ownedAttribute) {
    this.ownedAttribute = ownedAttribute;
  }

  @Override
  public PackageId getOwningPackage() {
    return owningPackage;
  }

  @Override
  public void setOwningPackage(PackageId owningPackage) {
    this.owningPackage = owningPackage;
  }
}
