package com.webuml.importer.parser;

import com.webuml.importer.parser.candidate.CandidateModel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class CandidateModelCreatorTest {

  private CandidateModelCreator candidateModelCreator;

  @BeforeMethod
  public void setUp() throws Exception {
    candidateModelCreator = new CandidateModelCreator();
  }

  @Test
  public void createCandidateModelForAllTestClasses() throws Exception {
    String absolutePath = new File("").getAbsolutePath();
    String rootDirectory = absolutePath + "/src/test/resources/java-classes/";
    List<VirtualFile> files = new FileWalkerHelper().walkDirectory(new File(rootDirectory));

    CandidateModel candidateModel = candidateModelCreator.getCandidateModel(files);
    assertNotNull(candidateModel);
    assertNotNull(candidateModel.getClassDeclarationCandidates());
    assertTrue(candidateModel.getClassDeclarationCandidates().size() > 0);
  }
}
