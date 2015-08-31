package com.webuml.importer.git.importprocess;

import com.webuml.importer.domain.BackEndModelInstance;
import com.webuml.importer.domain.primitives.ProjectId;
import com.webuml.importer.git.controller.ImportProcessStatus;
import com.webuml.importer.git.infrastructure.ImportAbilityStatus;
import com.webuml.importer.git.infrastructure.ImportHandler;
import com.webuml.importer.git.repository.GitProject;
import com.webuml.importer.git.repository.GitProjectRepository;
import com.webuml.importer.parser.SourceBasedModelCreator;
import com.webuml.importer.parser.VirtualFile;
import com.webuml.importer.projectmanagerclient.ProjectManagerHypermediaDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Provider;
import java.math.BigInteger;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

import static com.webuml.importer.git.controller.ImportProcessStatus.*;

@Component
public class ImportProcessService {

  private Logger logger = LoggerFactory.getLogger(ImportProcessService.class);

  private ProjectManagerHypermediaDocument projectManagerHypermediaDocument;

  @Inject
  GitProjectRepository gitProjectRepository;
  @Inject
  SourceBasedModelCreator sourceBasedModelCreatorImpl;
  @Inject
  RestMetaModelSender restMetaModelSender;
  @Autowired
  List<ImportHandler> importHandlers;
  @Inject
  void setProjectManagerHypermediaDocument(Provider<ProjectManagerHypermediaDocument> projectManagerClientProvider) {
    projectManagerHypermediaDocument = projectManagerClientProvider.get();
  }

  public GitProject getImportProcess(String id) {
    GitProject gitProject = gitProjectRepository.findOne(new BigInteger(id));
    if (gitProject == null) {
      throw new NoSuchElementException("No gitProcess found with ID=" + id);
    }
    return gitProject;
  }

  public GitProject updateImportProcessStatus(BigInteger id, ImportProcessStatus status) {
    GitProject gitProject = gitProjectRepository.findOne(id);
    if (gitProject == null) {
      throw new NoSuchElementException("No gitProcess found with ID=" + id);
    }
    gitProject.setStatus(status);
    gitProjectRepository.save(gitProject);
    return gitProject;
  }

  public void updateImportProcessProjectId(BigInteger id, ProjectId projectId) {
    GitProject gitProject = gitProjectRepository.findOne(id);
    if (gitProject == null) {
      throw new NoSuchElementException("No gitProcess found with ID=" + id);
    }
    gitProject.setProjectId(projectId);
    gitProjectRepository.save(gitProject);
  }

  private void updateImportProcessStatus(BigInteger id, UserPreferences userPreferences) {
    GitProject gitProject = gitProjectRepository.findOne(id);
    if (gitProject == null) {
      throw new NoSuchElementException("No gitProcess found with ID=" + id);
    }
    gitProject.setUserPreferences(userPreferences);
    gitProjectRepository.save(gitProject);
  }

  public ImportAbilityStatus canHandle(URI uri) {
    for (ImportHandler importHandler : importHandlers) {
      ImportAbilityStatus importAbilityStatus = importHandler.canHandle(uri);
      if (importAbilityStatus == ImportAbilityStatus.SUPPORTED) {
        return importAbilityStatus;
      }
    }
    return ImportAbilityStatus.NOT_SUPPORTED;
  }

  private ImportHandler getHandler(URI uri) {
    for (ImportHandler importHandler : importHandlers) {
      ImportAbilityStatus importAbilityStatus = importHandler.canHandle(uri);
      if (importAbilityStatus == ImportAbilityStatus.SUPPORTED) {
        return importHandler;
      }
    }
    return null;
  }

  public BigInteger createNew(URI url) {
    GitProject gitProject = new GitProject();
    gitProject.setUrl(url.toString());
    gitProject.setStatus(CONNECTING);
    GitProject saved = gitProjectRepository.save(gitProject);
    return saved.getId();
  }

  public BackEndModelInstance importProject(ImportProcessInput input) {
    try {
      logger.info("starting download for URL " + input.getGitUri());
      ProjectId projectId = projectManagerHypermediaDocument.createProjectId();
      updateImportProcessStatus(input.getImportProcessId(), input.getUserPreferences());
      updateImportProcessStatus(input.getImportProcessId(), DOWNLOADING);
      ImportHandler importHandler = getHandler(input.getGitUri());
      List<VirtualFile> virtualFiles = importHandler.create(input.getGitUri(), projectId);
      updateImportProcessStatus(input.getImportProcessId(), ANALYSE);

      BackEndModelInstance model = sourceBasedModelCreatorImpl.createModel(virtualFiles, projectId, input.getGitUri());
      logger.info("created model " + model.getProject().getName() + " and ID: " + model.getProject().getId());
      updateImportProcessStatus(input.getImportProcessId(), PREPARING_PROJECT);
      restMetaModelSender.send(model);
      updateImportProcessProjectId(input.getImportProcessId(), model.getProject().getId());
      updateImportProcessStatus(input.getImportProcessId(), DONE);
      return model;
    }
    catch (Exception e) {
      logger.error(e.getMessage());
      e.printStackTrace();
      updateImportProcessStatus(input.getImportProcessId(), ERROR);
    }
    return null;
  }
}
