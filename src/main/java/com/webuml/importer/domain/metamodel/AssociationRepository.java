package com.webuml.importer.domain.metamodel;

import com.webuml.importer.domain.primitives.ElementId;
import com.webuml.importer.domain.primitives.ProjectId;
import com.webuml.importer.domain.primitives.PropertyId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AssociationRepository extends CrudRepository<Association, ElementId> {

  Set<Association> findByProjectId(ProjectId projectId);

  Set<Association> findByOwnedEnd(PropertyId typeId);

  Set<Association> findByMemberEnd(PropertyId typeId);

  Set<Association> findByMemberEndIn(Set<PropertyId> typeId);
}
