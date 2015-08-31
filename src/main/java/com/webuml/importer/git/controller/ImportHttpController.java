package com.webuml.importer.git.controller;

import com.webuml.importer.git.importprocess.ImportProcessInput;
import com.webuml.importer.git.importprocess.ImportProcessService;
import com.webuml.importer.git.importprocess.UserPreferences;
import com.webuml.importer.git.infrastructure.EnvironmentPlaceholderReplacer;
import com.webuml.importer.git.infrastructure.ImportAbilityStatus;
import com.webuml.importer.git.repository.GitProject;
import com.webuml.importer.tools.IdShortener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;

import static com.webuml.importer.git.importprocess.UserPreferences.MAX_HEIGHT;
import static com.webuml.importer.git.importprocess.UserPreferences.MAX_WIDTH;
import static com.webuml.importer.git.importprocess.UserPreferences.MIN_HEIGHT;
import static com.webuml.importer.git.importprocess.UserPreferences.MIN_WIDTH;
import static com.webuml.importer.git.infrastructure.ImportAbilityStatus.SUPPORTED;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/importer")
public class ImportHttpController {

  private static Log logger = LogFactory.getLog(ImportHttpController.class);

  @Inject
  @Named("gitImportTaskExecutor")
  TaskExecutor gitImportTaskExecutor;

  @Inject
  ImportProcessService importProcessService;

  @Inject
  IdShortener idShortener;

  @Inject
  Provider<ImportProcessRunner> importProcessRunnerProvider;

  @Value("${projectManager.url}")
  String projectManagerUrl;

  @Inject
  EnvironmentPlaceholderReplacer placeholderReplacer;

  @RequestMapping(method = POST, value = "/git")
  public void importFromUri(@RequestParam(value = "git-uri", required = true) URI uri,
                            @RequestParam(value = "width", required = false) String width,
                            @RequestParam(value = "height", required = false) String height,
                            HttpServletResponse response) throws IOException {
    logger.info("importFromUri: " + uri.toString());
    ImportAbilityStatus status = importProcessService.canHandle(uri);
    if (status == SUPPORTED) {
      BigInteger id = importProcessService.createNew(uri);
      ImportProcessRunner runner = importProcessRunnerProvider.get();
      ImportProcessInput importProcessInput = new ImportProcessInput();
      importProcessInput.setUserPreferences(createUserPreferences(width, height));
      importProcessInput.setImportProcessId(id);
      importProcessInput.setGitUri(uri);
      runner.setImportProcessInput(importProcessInput);
      gitImportTaskExecutor.execute(runner);
      response.sendRedirect("/importer/process/" + id);
      response.setStatus(CREATED.value());
    } else {
      response.setContentType(TEXT_HTML_VALUE);
      response.setStatus(OK.value());
      placeholderReplacer.replaceContent("/templates/error.hbs", response.getOutputStream());
    }
  }

  @RequestMapping(method = GET, value = "/process/{id}")
  public String getProcess(@PathVariable("id") String id, HttpServletResponse response, final ModelMap model) throws IOException {
    logger.info("getProcess: " + id);

    GitProject importProcess = importProcessService.getImportProcess(id);

    if (ImportProcessStatus.DONE == importProcess.getStatus()) {
      String url = projectManagerUrl + "/viewModelCreation";
      url += "?projectId=" + importProcess.getProjectId();
      if (importProcess.getUserPreferences()!= null) {
        url += "&width=" + importProcess.getUserPreferences().getWindowWidth();
        url += "&height=" + importProcess.getUserPreferences().getWindowHeight();
      }
      response.sendRedirect(url);
      return null;
    }

    response.setStatus(ACCEPTED.value());
    // PROCESS
    model.put("process_uri", "/process/" + id);
    model.put("process_id", importProcess.getId().toString());
    model.put("process_shortId", idShortener.shorten(importProcess.getId()));
    model.put("process_url", importProcess.getUrl());
    model.put("process_status", importProcess.getStatus() != null ? importProcess.getStatus().toString() : "");
    model.put("process_status_DONE", ImportProcessStatus.DONE == importProcess.getStatus());
    // PROJECT
    model.put("project_id", importProcess.getProjectId());
    model.put("project_url", importProcess.getProjectId() != null ? projectManagerUrl + "/projects/" + importProcess.getProjectId() : null);
    return "classpath:templates/import-process.hbs";
  }

  UserPreferences createUserPreferences(String width, String height) {
    try {
      int w = Integer.parseInt(width);
      int h = Integer.parseInt(height);
      UserPreferences userPreferences = new UserPreferences();
      userPreferences.setWindowWidth(Math.max(MIN_WIDTH, Math.min(MAX_WIDTH, w)));
      userPreferences.setWindowHeight(Math.max(MIN_HEIGHT, Math.min(MAX_HEIGHT, h)));
      return userPreferences;
    } catch (NumberFormatException e) {
      return null;
    }
  }
}
