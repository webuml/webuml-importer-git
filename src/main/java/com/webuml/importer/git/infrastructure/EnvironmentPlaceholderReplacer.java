package com.webuml.importer.git.infrastructure;

import org.apache.commons.io.IOUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.PropertyPlaceholderHelper;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;

@Component
public class EnvironmentPlaceholderReplacer {

  @Inject
  Environment environment;

  PropertyPlaceholderHelper placeholderHelper = new PropertyPlaceholderHelper("${", "}");

  public String replacePlaceholder(String content) {
    return placeholderHelper.replacePlaceholders(content, environment::getProperty);
  }

  public void replaceContent(String path, ServletOutputStream outputStream) throws IOException {
    String content = IOUtils.toString(new ClassPathResource(path).getInputStream());
    String contentFirstStep = replacePlaceholder(content);
    PropertyPlaceholderHelper placeholderHelper = new PropertyPlaceholderHelper("{{", "}}");
    Properties properties = new Properties();
    properties.put("error_message", "URLtype not supported!");
    String finalString = placeholderHelper.replacePlaceholders(contentFirstStep, properties);
    IOUtils.copy(new ByteArrayInputStream(finalString.getBytes()), outputStream);
  }
}
