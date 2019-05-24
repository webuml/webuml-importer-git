package com.webuml.importer.domain.metamodel;

import com.webuml.importer.domain.primitives.ElementId;
import com.webuml.importer.domain.primitives.PropertyId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PropertyRepository extends CrudRepository<Property, PropertyId> {

  Set<Property> findByTypeId(ElementId typeId);
}
