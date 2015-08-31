package com.webuml.importer.parser;

import com.webuml.importer.domain.BackEndModelInstance;
import com.webuml.importer.domain.metamodel.Association;
import com.webuml.importer.domain.metamodel.Class;
import com.webuml.importer.parser.candidate.ClassDeclarationCandidate;
import com.webuml.importer.parser.candidate.ClassRelationCandidate;
import com.webuml.importer.parser.candidate.PackageResolveState;
import org.testng.annotations.Test;

import java.net.URI;

import static org.testng.Assert.assertEquals;

public class ElementModelCreatorAssociationTest extends ElementModelCreatorTest {

  private static final URI URL = URI.create("https://github.com/libgit2/libgit2/archive/development.zip");

  @Test
  public void unidirectionalAssociation() throws Exception {
    ClassDeclarationCandidate className1 = newCandidate("ClassName1", "com.webuml.model1");
    ClassDeclarationCandidate className2 = newCandidate("ClassName2", "com.webuml.model2");

    ClassRelationCandidate candidate = new ClassRelationCandidate();
    candidate.setToClassName(className2.getClassName());
    candidate.setToPackageName(className2.getPackageName());
    candidate.setPackageResolveState(PackageResolveState.RESOLVED);
    className1.getClassRelationCandidates().add(candidate);

    BackEndModelInstance backEndModelInstance = elementModelCreator.createBackEndModelInstance(candidateModel, PROJECT_ID, URL);

    assertEquals(backEndModelInstance.getClasses().size(), 2);
    Class classElement1 = backEndModelInstance.getClasses().get(0);
    assertEquals(classElement1.getName(), className1.getClassName());

    Class classElement2 = backEndModelInstance.getClasses().get(1);
    assertEquals(classElement2.getName(), className2.getClassName());
    assertEquals(backEndModelInstance.getAssociations().size(), 1);
    assertEquals(backEndModelInstance.getProperties().size(), 2);

    Association association = backEndModelInstance.getAssociations().stream().findFirst().get();

    assertEquals(association.getOwnedEnd().size(), 1);
    assertEquals(association.getMemberEnd().size(), 2);
  }

  @Test
  public void noAssociationAsReferencedTypeIsNotInScope() throws Exception {
    ClassDeclarationCandidate className1 = newCandidate("ClassName1", "com.webuml.model1");

    ClassRelationCandidate candidate = new ClassRelationCandidate();
    candidate.setToClassName("OtherCkass");
    candidate.setToPackageName("org.library");
    candidate.setPackageResolveState(PackageResolveState.RESOLVED);
    className1.getClassRelationCandidates().add(candidate);

    BackEndModelInstance backEndModelInstance = elementModelCreator.createBackEndModelInstance(candidateModel, PROJECT_ID, URL);

    assertEquals(backEndModelInstance.getClasses().size(), 1);
    Class classElement1 = backEndModelInstance.getClasses().get(0);
    assertEquals(classElement1.getName(), className1.getClassName());

    assertEquals(backEndModelInstance.getAssociations().size(), 0);
    assertEquals(backEndModelInstance.getProperties().size(), 1);
  }
}
