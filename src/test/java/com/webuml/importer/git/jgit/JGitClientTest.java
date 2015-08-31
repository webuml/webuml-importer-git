package com.webuml.importer.git.jgit;

import com.webuml.importer.parser.VirtualFile;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class JGitClientTest {

  private String gitRepoLocation;
  private GitRepositoryInstance repositoryInstance;

  @BeforeClass
  public void setUp() throws Exception {
    repositoryInstance = new GitRepositoryInstance(new JGitTestConfiguration());
    gitRepoLocation = "test-" + UUID.randomUUID();
  }

  @Test
  public void checkOutRepo() throws Exception {
    repositoryInstance.checkoutRepo("https://github.com/hypoport/MockInjector.git", gitRepoLocation);
  }


  @Test(dependsOnMethods = "checkOutRepo")
  public void testGetCommits() throws Exception {
    List<VirtualFile> lastChanges = repositoryInstance.getLastChanges();
    printChanges(lastChanges);
    assertTrue(lastChanges.size() > 0);
  }

  @Test(dependsOnMethods = "checkOutRepo")
  public void testFilesForCommit() throws Exception {
    String initialCommit = "81ce35bafa2eb170b0f53a75fd3706ba2d2dba7c";
    List<VirtualFile> currentFiles = repositoryInstance.getCurrentFilesFor(initialCommit);
    printChanges(currentFiles);
    assertEquals(currentFiles.size(), 22);
  }

  private void printChanges(List<VirtualFile> changes) {
    //TODO martinklewitz unnecessary in the future
    // just for convenience
    System.out.println("files: " + changes.size());
    changes.forEach(change -> {
      System.out.println("file: " + change.getFileName());
      StringWriter writer = new StringWriter();
      try {
        IOUtils.copy(change.getFileStream(), writer, "UTF-8");
      }
      catch (IOException e) {
        e.printStackTrace();
      }
      String theString = writer.toString();
      System.out.println("content: " + theString);
    });
  }
}
