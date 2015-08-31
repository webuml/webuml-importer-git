package com.webuml.importer.parser.java;

import com.webuml.importer.parser.candidate.ClassCandidateRelationType;
import com.webuml.importer.parser.candidate.ClassDeclarationCandidate;
import com.webuml.importer.parser.candidate.ClassRelationCandidate;
import com.webuml.importer.parser.candidate.PackageResolveState;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class ClassWithMemberAndImportJavaParserTest extends AbstractJavaParserTest {

  @Test
  public void classWithMemberAndImport() throws Exception {
    List<ClassDeclarationCandidate> classDeclarationCandidates = parse("ClassWithMemberAndImport.java");

    assertNotNull(classDeclarationCandidates);
    assertTrue(classDeclarationCandidates.size() == 1);
    ClassDeclarationCandidate declaration = classDeclarationCandidates.get(0);
    assertEquals(declaration.getClassName(), "ClassWithMemberAndImport");
    assertEquals(declaration.getPackageName(), "com.webuml.parser.java.test");

    assertNotNull(declaration.getClassRelationCandidates());
    assertTrue(declaration.getClassRelationCandidates().size() == 1);
    ClassRelationCandidate classRelation = declaration.getClassRelationCandidates().get(0);
    assertEquals(classRelation.getToClassName(), "SecondSimpleClass");
    assertEquals(classRelation.getAttributeName(), "member");
    assertEquals(classRelation.getToPackageName(), "com.webuml.parser.java.test.other");
    assertEquals(classRelation.getPackageResolveState(), PackageResolveState.RESOLVED);
    assertEquals(classRelation.getRelationType(), ClassCandidateRelationType.MEMBER);
  }
}
