package com.webuml.importer.domain.metamodel;

import com.webuml.importer.domain.primitives.Entity;
import com.webuml.importer.domain.primitives.PackageId;
import com.webuml.importer.domain.primitives.ProjectId;

public class Package extends Entity<PackageId> implements NamedElement, PackageableElement, Type, Element<PackageId>, ProjectIdHolder {

  ProjectId projectId;

  String name;
  private PackageId owningPackage;

  public Package() {
    super(PackageId.class);
  }

  public Package(PackageId id) {
    super(id);
  }

  @Override
  public String toString() {
    return getName();
  }

  public ProjectId getProjectId() {
    return projectId;
  }

  public void setProjectId(ProjectId projectId) {
    this.projectId = projectId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
