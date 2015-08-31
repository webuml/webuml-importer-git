package com.webuml.importer.parser.java;

import com.webuml.importer.parser.candidate.ClassDeclarationCandidate;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class ClassWithSyntaxErrorParserTest extends AbstractJavaParserTest {

  @Test
  public void syntaxErrorsAreDiscarded() throws Exception {
    List<ClassDeclarationCandidate> classDeclarationCandidates = parse("ClassWithSyntaxError.java");

    assertNotNull(classDeclarationCandidates);
    assertTrue(classDeclarationCandidates.size() == 0);
  }
}
