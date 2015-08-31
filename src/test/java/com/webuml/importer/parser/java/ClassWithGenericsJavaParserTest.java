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

public class ClassWithGenericsJavaParserTest extends AbstractJavaParserTest {

  @Test
  public void classWithMemberAndImport() throws Exception {
    List<ClassDeclarationCandidate> classDeclarationCandidates = parse("ClassWithGenerics.java");

    assertNotNull(classDeclarationCandidates);
    assertTrue(classDeclarationCandidates.size() == 1);
    ClassDeclarationCandidate declaration = classDeclarationCandidates.get(0);
    assertEquals(declaration.getClassName(), "ClassWithGenerics");
    assertEquals(declaration.getPackageName(), "com.webuml.parser.java.test");

    assertNotNull(declaration.getClassRelationCandidates());
    assertTrue(declaration.getClassRelationCandidates().size() == 2);

    ClassRelationCandidate relation1 = declaration.getClassRelationCandidates().get(0);
    assertEquals(relation1.getToClassName(), "SecondSimpleClass");
    assertEquals(relation1.getAttributeName(), "arrayOfSecondSimpleClasses");
    assertEquals(relation1.getToPackageName(), "com.webuml.parser.java.test.other");
    assertEquals(relation1.getPackageResolveState(), PackageResolveState.RESOLVED);
    assertEquals(relation1.getRelationType(), ClassCandidateRelationType.MEMBER);

    ClassRelationCandidate relation2 = declaration.getClassRelationCandidates().get(1);
    assertEquals(relation2.getToClassName(), "List");
    assertEquals(relation2.getAttributeName(), "listOfSecondSimpleClasses");
    assertEquals(relation2.getToPackageName(), "java.util");
    assertEquals(relation2.getPackageResolveState(), PackageResolveState.RESOLVED);
    assertEquals(relation2.getRelationType(), ClassCandidateRelationType.MEMBER);
  }
}
