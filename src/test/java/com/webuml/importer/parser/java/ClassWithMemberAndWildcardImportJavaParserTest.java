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

public class ClassWithMemberAndWildcardImportJavaParserTest extends AbstractJavaParserTest {

  @Test
  public void classWithMemberAndWildcardImport() throws Exception {
    List<ClassDeclarationCandidate> classDeclarationCandidates = parse("ClassWithMemberWildcardImport.java");

    assertNotNull(classDeclarationCandidates);
    assertTrue(classDeclarationCandidates.size() == 1);
    ClassDeclarationCandidate declaration = classDeclarationCandidates.get(0);
    assertEquals(declaration.getClassName(), "ClassWithMemberWildcardImport");
    assertEquals(declaration.getPackageName(), "com.webuml.parser.java.test");

    assertNotNull(declaration.getClassRelationCandidates());
    assertTrue(declaration.getClassRelationCandidates().size() == 1);
    ClassRelationCandidate classRelation = declaration.getClassRelationCandidates().get(0);
    assertEquals(classRelation.getToClassName(), "SecondSimpleClass");
    assertEquals(classRelation.getToPackageName(), null);
    assertEquals(classRelation.getAttributeName(), "member");
    assertEquals(classRelation.getPackageResolveState(), PackageResolveState.WILDCARD);
    assertEquals(classRelation.getRelationType(), ClassCandidateRelationType.MEMBER);
    assertNotNull(classRelation.getPossibleToPackageNames());
    assertEquals(classRelation.getPossibleToPackageNames().size(), 1);
    assertEquals(classRelation.getPossibleToPackageNames().get(0), "com.webuml.parser.java.test.other");
  }
}
