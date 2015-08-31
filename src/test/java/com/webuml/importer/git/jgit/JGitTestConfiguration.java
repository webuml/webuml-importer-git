package com.webuml.importer.git.jgit;

import java.io.File;

public class JGitTestConfiguration implements JGitConfiguration {

  @Override
  public String getRepoUrl() {
    return new File("").getAbsolutePath() + "/" + "target/";
  }

  @Override
  public String getGitBaseDirectory(String fileDirectory) {
    return getRepoUrl() + fileDirectory;
  }
}
