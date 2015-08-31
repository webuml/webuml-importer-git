package com.webuml.importer.parser.java;

import com.webuml.importer.parser.candidate.ClassDeclarationCandidate;
import com.webuml.importer.parser.candidate.ClassRelationCandidate;
import com.webuml.importer.parser.candidate.PackageResolveState;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class ClassWithMemberJavaParserTest extends AbstractJavaParserTest {

  @Test
  public void classWithMember() throws Exception {
    List<ClassDeclarationCandidate> classDeclarationCandidates = parse("ClassWithMemberNoImport.java");

    assertNotNull(classDeclarationCandidates);
    assertTrue(classDeclarationCandidates.size() == 1);
    ClassDeclarationCandidate declaration = classDeclarationCandidates.get(0);
    assertEquals(declaration.getClassName(), "ClassWithMemberNoImport");
    assertEquals(declaration.getPackageName(), "com.webuml.parser.java.test");
    assertNotNull(declaration.getClassRelationCandidates());
    assertTrue(declaration.getClassRelationCandidates().size() == 1);
    ClassRelationCandidate classRelation = declaration.getClassRelationCandidates().get(0);
    assertEquals(classRelation.getToClassName(), "SimpleClass");
    assertEquals(classRelation.getPackageResolveState(), PackageResolveState.UNRESOLVED);
  }
}
