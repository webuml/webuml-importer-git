package com.webuml.importer.parser.java;

import com.webuml.importer.parser.candidate.ClassDeclarationCandidate;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class ClassWithoutPackageJavaParserTest extends AbstractJavaParserTest {

  @Test
  public void findMembers() throws Exception {
    List<ClassDeclarationCandidate> classDeclarationCandidates = parse("ClassWithoutPackage.java");

    assertNotNull(classDeclarationCandidates);
    assertTrue(classDeclarationCandidates.size() == 1);
    assertEquals(classDeclarationCandidates.get(0).getClassName(), "ClassWithoutPackage");
    assertEquals(classDeclarationCandidates.get(0).getPackageName(), "");
  }
}
