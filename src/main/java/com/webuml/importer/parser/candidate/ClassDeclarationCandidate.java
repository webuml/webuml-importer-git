package com.webuml.importer.parser.candidate;

import java.util.ArrayList;
import java.util.List;

public class ClassDeclarationCandidate {

  String packageName;
  String className;
  List<ClassRelationCandidate> classRelationCandidates = new ArrayList<>();

  public String getPackageName() {
    return packageName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public List<ClassRelationCandidate> getClassRelationCandidates() {
    return classRelationCandidates;
  }

  public void setClassRelationCandidates(List<ClassRelationCandidate> classRelationCandidates) {
    this.classRelationCandidates = classRelationCandidates;
  }
}
