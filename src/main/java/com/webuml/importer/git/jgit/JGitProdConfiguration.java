package com.webuml.importer.git.jgit;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class JGitProdConfiguration implements JGitConfiguration {

  @Override
  public String getRepoUrl() {
    return new File("").getAbsolutePath() + "/" + "target/";
  }

  @Override
  public String getGitBaseDirectory(String fileDirectory) {
    return getRepoUrl() + fileDirectory;
  }
}
