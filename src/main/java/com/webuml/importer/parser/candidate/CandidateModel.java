package com.webuml.importer.parser.candidate;

import java.util.ArrayList;
import java.util.List;

public class CandidateModel {

  List<ClassDeclarationCandidate> classDeclarationCandidates = new ArrayList<>();

  public List<ClassDeclarationCandidate> getClassDeclarationCandidates() {
    return classDeclarationCandidates;
  }

  public void setClassDeclarationCandidates(List<ClassDeclarationCandidate> classDeclarationCandidates) {
    this.classDeclarationCandidates = classDeclarationCandidates;
  }
}
