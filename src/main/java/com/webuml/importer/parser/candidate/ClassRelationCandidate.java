package com.webuml.importer.parser.candidate;

import java.util.ArrayList;
import java.util.List;

public class ClassRelationCandidate {

  String toPackageName;
  String toClassName;
  String attributeName;
  ClassCandidateRelationType relationType;
  PackageResolveState packageResolveState;
  List<String> possibleToPackageNames = new ArrayList<>();

  public String getToPackageName() {
    return toPackageName;
  }

  public void setToPackageName(String toPackageName) {
    this.toPackageName = toPackageName;
  }

  public String getToClassName() {
    return toClassName;
  }

  public void setToClassName(String toClassName) {
    this.toClassName = toClassName;
  }

  public ClassCandidateRelationType getRelationType() {
    return relationType;
  }

  public void setRelationType(ClassCandidateRelationType relationType) {
    this.relationType = relationType;
  }

  public PackageResolveState getPackageResolveState() {
    return packageResolveState;
  }

  public void setPackageResolveState(PackageResolveState packageResolveState) {
    this.packageResolveState = packageResolveState;
  }

  public List<String> getPossibleToPackageNames() {
    return possibleToPackageNames;
  }

  public void setPossibleToPackageNames(List<String> possibleToPackageNames) {
    this.possibleToPackageNames = possibleToPackageNames;
  }

  public String getAttributeName() {
    return attributeName;
  }

  public void setAttributeName(String attributeName) {
    this.attributeName = attributeName;
  }
}
