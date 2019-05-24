package com.webuml.importer.domain.metamodel;

import com.webuml.importer.domain.primitives.ElementId;
import com.webuml.importer.domain.primitives.ProjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackagesRepository extends CrudRepository<Package, ElementId> {

  List<Package> findByProjectId(ProjectId projectId);
}
