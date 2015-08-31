package com.webuml.importer.git.github;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.testng.annotations.Test;

import java.util.List;

public class GitHubClientTest {

  @Test(enabled = false)
  public void testName() throws Exception {
    GitHub github = GitHub.connectAnonymously();
    GHRepository repo = github.getRepository("martinklewitz/Prefuse");
    List<GHContent> directoryContent = repo.getDirectoryContent("");
    GHContent fileContent = repo.getFileContent("build.xml");
    String content = fileContent.getContent();
  }
}
