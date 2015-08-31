package com.webuml.importer.parser;

import com.webuml.importer.parser.candidate.ClassDeclarationCandidate;
import org.antlr.v4.runtime.CharStream;

import java.util.List;

public interface SourceParser {

  String getSupportedFileType();

  public List<ClassDeclarationCandidate> parse(CharStream stream);
}
