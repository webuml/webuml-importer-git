package com.webuml.importer.domain.metamodel;

import com.webuml.importer.domain.primitives.ElementId;
import com.webuml.importer.domain.primitives.PropertyId;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface PropertyRepository extends CrudRepository<Property, PropertyId> {

  Set<Property> findByTypeId(ElementId typeId);
}
