package com.webuml.importer.domain.projectmodel;

import com.webuml.importer.domain.primitives.ProjectId;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, ProjectId> {

}
