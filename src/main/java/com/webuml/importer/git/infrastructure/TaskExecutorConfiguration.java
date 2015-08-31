package com.webuml.importer.git.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
public class TaskExecutorConfiguration {

  @Bean(name="gitImportTaskExecutor")
  TaskExecutor gitImportTaskExecutor() {
    SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
    simpleAsyncTaskExecutor.setDaemon(true);
    simpleAsyncTaskExecutor.setThreadNamePrefix("git-import");
    simpleAsyncTaskExecutor.setConcurrencyLimit(3);
    return simpleAsyncTaskExecutor;
  }
}
