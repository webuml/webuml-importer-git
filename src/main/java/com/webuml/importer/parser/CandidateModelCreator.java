package com.webuml.importer.parser;

import com.webuml.importer.parser.candidate.CandidateModel;
import com.webuml.importer.parser.candidate.ClassDeclarationCandidate;
import com.webuml.importer.parser.java.JavaParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CandidateModelCreator {

  private List<SourceParser> sourceParsers = new ArrayList<>();

  public CandidateModelCreator() {
    this.sourceParsers.add(new JavaParser());
  }

  public CandidateModel getCandidateModel(List<VirtualFile> files) {
    return new Stateful().createModel(files);
  }

  private class Stateful {

    private List<ClassDeclarationCandidate> classDeclarations;

    private Stateful() {
      this.classDeclarations = new ArrayList<>();
    }

    public CandidateModel createModel(List<VirtualFile> files) {
      files.forEach(file -> parseFile(file));
      CandidateModel candidateModel = new CandidateModel();
      candidateModel.setClassDeclarationCandidates(classDeclarations);
      return candidateModel;
    }

    private void parseFile(VirtualFile virtualFile) {
      String name = virtualFile.getFileName();
      int index = name.lastIndexOf(".");
      if (index > 0) {
        String fileExtension = name.substring(index + 1);
        SourceParser parser = findParser(fileExtension);
        if (parser != null) {
          try {
            ANTLRInputStream stream = new ANTLRInputStream(virtualFile.getFileStream());
            List<ClassDeclarationCandidate> newDeclarations = parser.parse(stream);
            classDeclarations.addAll(newDeclarations);
          }
          catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }

    private SourceParser findParser(String fileExtension) {
      for (SourceParser sourceParser : sourceParsers) {
        if (sourceParser.getSupportedFileType().equals(fileExtension)) {
          return sourceParser;
        }
      }
      return null;
    }
  }
}
