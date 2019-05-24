package com.webuml.importer.domain.metamodel;

import com.webuml.importer.domain.primitives.ElementId;
import com.webuml.importer.domain.primitives.ProjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends CrudRepository<Class, ElementId> {

  List<Class> findByProjectId(ProjectId projectId);
}
