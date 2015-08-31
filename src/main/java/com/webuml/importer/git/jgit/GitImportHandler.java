package com.webuml.importer.git.jgit;

import com.webuml.importer.domain.primitives.ProjectId;
import com.webuml.importer.git.infrastructure.ImportAbilityStatus;
import com.webuml.importer.git.infrastructure.ImportHandler;
import com.webuml.importer.parser.VirtualFile;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.net.URI;
import java.util.List;

import static com.webuml.importer.git.infrastructure.ImportAbilityStatus.NOT_SUPPORTED;
import static com.webuml.importer.git.infrastructure.ImportAbilityStatus.SUPPORTED;

@Component
public class GitImportHandler implements ImportHandler {

  String ZIP_MATCHER = ".+\\.git";

  @Inject
  JGitConfiguration gitConfiguration;

  @Override
  public ImportAbilityStatus canHandle(URI uri) {
    String url = uri.toString();
    boolean matches = url.matches(ZIP_MATCHER);
    return matches ? SUPPORTED : NOT_SUPPORTED;
  }

  @Override
  public List<VirtualFile> create(URI uri, ProjectId projectId) throws Exception {
    GitRepositoryInstance gitRepositoryInstance = new GitRepositoryInstance(gitConfiguration);
    gitRepositoryInstance.checkoutRepo(uri.toASCIIString(), projectId.getValue());
    List<VirtualFile> currentFiles = gitRepositoryInstance.getCurrentFilesFor("master");
    return currentFiles;
  }
}
