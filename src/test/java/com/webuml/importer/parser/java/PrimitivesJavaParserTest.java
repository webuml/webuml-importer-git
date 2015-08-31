package com.webuml.importer.parser.java;

import com.webuml.importer.parser.candidate.ClassDeclarationCandidate;
import com.webuml.importer.parser.candidate.ClassRelationCandidate;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class PrimitivesJavaParserTest extends AbstractJavaParserTest {

  @Test
  public void testPrimitives() throws Exception {
    List<ClassDeclarationCandidate> classDeclarationCandidates = parse("ClassWithPrimitives.java");

    assertNotNull(classDeclarationCandidates);
    assertTrue(classDeclarationCandidates.size() == 1);
    ClassDeclarationCandidate classDeclarationCandidate = classDeclarationCandidates.get(0);
    assertEquals(classDeclarationCandidate.getClassName(), "ClassWithPrimitives");
    assertEquals(classDeclarationCandidate.getPackageName(), "com.webuml.parser.java.test");
    List<ClassRelationCandidate> classRelationCandidates = classDeclarationCandidate.getClassRelationCandidates();
    assertEquals(classRelationCandidates.size(), 5);
    assertEquals(classRelationCandidates.get(0).getToClassName(), "boolean");
    assertEquals(classRelationCandidates.get(0).getAttributeName(), "aBoolean");
    assertEquals(classRelationCandidates.get(1).getToClassName(), "char");
    assertEquals(classRelationCandidates.get(1).getAttributeName(), "aChar");
    assertEquals(classRelationCandidates.get(2).getToClassName(), "short");
    assertEquals(classRelationCandidates.get(2).getAttributeName(), "aShort");
    assertEquals(classRelationCandidates.get(3).getToClassName(), "int");
    assertEquals(classRelationCandidates.get(3).getAttributeName(), "anInt");
    assertEquals(classRelationCandidates.get(4).getToClassName(), "long");
    assertEquals(classRelationCandidates.get(4).getAttributeName(), "aLong");
  }
}
