package com.webuml.importer.parser.java;

import com.webuml.importer.parser.candidate.ClassDeclarationCandidate;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AbstractJavaParserTest {

  private JavaParser javaParser;

  @BeforeMethod
  public void setUp() throws Exception {
    javaParser = new JavaParser();
  }

  protected List<ClassDeclarationCandidate> parse(String fileName) throws IOException {
    File file = new File("");
    String absolutePath = file.getAbsolutePath();

    String filePath = absolutePath + "/src/test/resources/java-classes/" + fileName;
    return javaParser.parse(new ANTLRFileStream(filePath));
  }
}
