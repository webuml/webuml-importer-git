package com.webuml.importer.git.importprocess;

import com.webuml.importer.domain.BackEndModelInstance;
import com.webuml.importer.domain.metamodel.AssociationRepository;
import com.webuml.importer.domain.metamodel.ClassRepository;
import com.webuml.importer.domain.metamodel.PackagesRepository;
import com.webuml.importer.domain.metamodel.PropertyRepository;
import com.webuml.importer.domain.projectmodel.ProjectRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class MetaModelPersister {

  @Inject
  ProjectRepository projectRepository;
  @Inject
  ClassRepository classRepository;
  @Inject
  AssociationRepository associationRepository;
  @Inject
  PropertyRepository propertyRepository;
  @Inject
  PackagesRepository packagesRepository;

  public void persist(BackEndModelInstance backEndModelInstance) {
    projectRepository.save(backEndModelInstance.getProject());

    backEndModelInstance.getPackages().forEach(myPackage -> packagesRepository.save(myPackage));
    backEndModelInstance.getClasses().forEach(clazz -> classRepository.save(clazz));
    backEndModelInstance.getProperties().forEach(property -> propertyRepository.save(property));
    backEndModelInstance.getAssociations().forEach(association -> associationRepository.save(association));
  }
}
