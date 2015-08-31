package com.webuml.importer.domain.metamodel;

import com.webuml.importer.domain.primitives.ElementId;
import com.webuml.importer.domain.primitives.ProjectId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PackagesRepository extends CrudRepository<Package, ElementId> {

  List<Package> findByProjectId(ProjectId projectId);
}
