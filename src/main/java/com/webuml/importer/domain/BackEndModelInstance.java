package com.webuml.importer.domain;

import com.webuml.importer.domain.metamodel.*;
import com.webuml.importer.domain.metamodel.Class;
import com.webuml.importer.domain.metamodel.Package;
import com.webuml.importer.domain.projectmodel.Project;

import java.util.List;
import java.util.Set;

public class BackEndModelInstance {

  Project project;
  Set<Association> associations;
  Set<Property> properties;
  List<Class> clazzes;
  List<Package> packages;

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public Set<Association> getAssociations() {
    return associations;
  }

  public void setAssociations(Set<Association> associations) {
    this.associations = associations;
  }

  public List<Class> getClasses() {
    return clazzes;
  }

  public void setClasses(List<com.webuml.importer.domain.metamodel.Class> clazzes) {
    this.clazzes = clazzes;
  }

  public List<Package> getPackages() {
    return packages;
  }

  public void setPackages(List<Package> packages) {
    this.packages = packages;
  }

  public Set<Property> getProperties() {
    return properties;
  }

  public void setProperties(Set<Property> properties) {
    this.properties = properties;
  }
}
