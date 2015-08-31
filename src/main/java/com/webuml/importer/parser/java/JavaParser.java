package com.webuml.importer.parser.java;

import com.webuml.importer.parser.SourceParser;
import com.webuml.importer.parser.candidate.ClassDeclarationCandidate;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DiagnosticErrorListener;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.misc.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class JavaParser implements SourceParser {

  private Logger logger = LoggerFactory.getLogger(JavaParser.class);

  @Override
  public String getSupportedFileType() {
    return "java";
  }

  @Override
  public List<ClassDeclarationCandidate> parse(CharStream stream) {
    try {
      Java8ModelGenerationStatefulListener stateful = new Java8ModelGenerationStatefulListener();

      Lexer lexer = new Java8Lexer(stream);
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      Java8Parser parser = new Java8Parser(tokens);
      parser.addParseListener(stateful);
      parser.addErrorListener(new DiagnosticErrorListener());
      parser.setErrorHandler(new BailErrorStrategy());
      parser.getInterpreter().setPredictionMode(PredictionMode.SLL);

      Java8Parser.CompilationUnitContext compilationUnit = parser.compilationUnit();
      parser.setBuildParseTree(false);
      compilationUnit.inspect(parser);

      return stateful.getClassDeclarationCandidates();
    }
    catch (Exception e) {
      logger.error("parser exception: " + e + " in source: " + stream.getText(new Interval(0, stream.size())));
      e.printStackTrace();
    }
    return new ArrayList<>();
  }
}
