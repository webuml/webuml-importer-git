package com.webuml.importer.domain.metamodel;

import com.webuml.importer.domain.primitives.ElementId;
import com.webuml.importer.domain.primitives.Entity;
import com.webuml.importer.domain.primitives.ProjectId;

public abstract class Classifier extends Entity<ElementId> implements NamedElement, Type, Element<ElementId>, ProjectIdHolder {

  ProjectId projectId;

  String name;

  public Classifier() {
    super(ElementId.class);
  }

  public Classifier(ElementId id) {
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
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
