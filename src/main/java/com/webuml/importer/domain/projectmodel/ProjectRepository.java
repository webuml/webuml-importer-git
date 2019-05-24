package com.webuml.importer.domain.projectmodel;

import com.webuml.importer.domain.primitives.ProjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, ProjectId> {

}
