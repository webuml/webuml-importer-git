package com.webuml.importer.domain.metamodel;

import com.webuml.importer.domain.primitives.ElementId;
import com.webuml.importer.domain.primitives.PropertyId;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AssociationResolver {

  @Inject
  PropertyRepository propertyRepository;

  @Inject
  AssociationRepository associationRepository;

  // TODO Faster with mongo map-reduce or group feature?
  public Set<Association> getAssociations(ElementId id) {
    Set<Property> properties = propertyRepository.findByTypeId(id);
    Set<PropertyId> propertyIds = properties.stream().<PropertyId>map(Property::getId).collect(Collectors.toSet());
    return associationRepository.findByMemberEndIn(propertyIds);
  }
}
