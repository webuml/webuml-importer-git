package com.webuml.importer.domain.metamodel;

import com.webuml.importer.domain.primitives.PackageId;

public interface PackageableElement {

  PackageId getOwningPackage();

  void setOwningPackage(PackageId packageId);
}
