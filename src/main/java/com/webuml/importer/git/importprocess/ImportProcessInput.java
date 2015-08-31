package com.webuml.importer.git.importprocess;

import java.math.BigInteger;
import java.net.URI;

public class ImportProcessInput {

  private URI gitUri;
  private BigInteger importProcessId;
  private UserPreferences userPreferences;

  public URI getGitUri() {
    return gitUri;
  }

  public void setGitUri(URI gitUri) {
    this.gitUri = gitUri;
  }

  public BigInteger getImportProcessId() {
    return importProcessId;
  }

  public void setImportProcessId(BigInteger importProcessId) {
    this.importProcessId = importProcessId;
  }

  public UserPreferences getUserPreferences() {
    return userPreferences;
  }

  public void setUserPreferences(UserPreferences userPreferences) {
    this.userPreferences = userPreferences;
  }
}
