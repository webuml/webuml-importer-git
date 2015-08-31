package com.webuml.importer.domain.projectmodel;

import com.webuml.importer.domain.metamodel.NamedElement;
import com.webuml.importer.domain.primitives.Entity;
import com.webuml.importer.domain.primitives.ProjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "projects")
public class Project extends Entity<ProjectId> implements NamedElement {

  private String name;

  public Project() {
    super(ProjectId.class);
  }

  public Project(ProjectId id) {
    super(id);
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
