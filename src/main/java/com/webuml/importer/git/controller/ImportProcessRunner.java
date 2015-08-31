package com.webuml.importer.git.controller;

import com.webuml.importer.git.importprocess.ImportProcessInput;
import com.webuml.importer.git.importprocess.ImportProcessService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
class ImportProcessRunner implements Runnable {

  @Inject
  ImportProcessService importProcessService;

  private ImportProcessInput importProcessInput;

  @Override
  public void run() {
    assert importProcessInput != null;
    importProcessService.importProject(importProcessInput);
  }

  public void setImportProcessInput(ImportProcessInput importProcessInput) {
    this.importProcessInput = importProcessInput;
  }
}
