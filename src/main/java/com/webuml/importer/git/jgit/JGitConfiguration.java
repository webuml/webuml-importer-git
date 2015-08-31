package com.webuml.importer.git.jgit;

public interface JGitConfiguration {

  public String getRepoUrl();

  public String getGitBaseDirectory(String projectName);
}
