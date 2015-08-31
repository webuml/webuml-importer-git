package com.webuml.importer.git.importprocess;

import com.webuml.importer.domain.BackEndModelInstance;
import com.webuml.importer.domain.projectmodel.ProjectRepository;
import com.webuml.importer.git.IntegrationTestWithMongoDB;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.math.BigInteger;
import java.net.URI;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class ImportProcessServiceTest extends IntegrationTestWithMongoDB {

  @Inject
  ImportProcessService importProcessService;

  @Inject
  ProjectRepository projectRepository;

  @Test(enabled = false)
  public void testImport() throws Exception {
    URI uri = new URI("https://github.com/hypoport/MockInjector/archive/master.zip");
    BigInteger newId = importProcessService.createNew(uri);
    ImportProcessInput importProcessInput = new ImportProcessInput();
    importProcessInput.setGitUri(uri);
    importProcessInput.setImportProcessId(newId);

    BackEndModelInstance backEndModel = importProcessService.importProject(importProcessInput);

    assertNotNull(backEndModel);
    assertTrue(backEndModel.getClasses().size() > 0);
    assertTrue(backEndModel.getPackages().size() > 0);
  }
}
