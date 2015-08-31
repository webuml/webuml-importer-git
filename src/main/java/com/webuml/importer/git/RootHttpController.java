package com.webuml.importer.git;

import com.webuml.importer.git.infrastructure.EnvironmentPlaceholderReplacer;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/")
public class RootHttpController {

  static final Charset UTF8 = Charset.forName("UTF-8");

  @Inject
  EnvironmentPlaceholderReplacer placeholderReplacer;

  @RequestMapping(method = GET, produces = "text/html")
  @ResponseStatus(FOUND)
  public void getIndexHtml(HttpServletResponse response) throws IOException {
    String content = IOUtils.toString(new ClassPathResource("index.html").getInputStream());
    String contentFinal = placeholderReplacer.replacePlaceholder(content);
    response.getOutputStream().write(contentFinal.getBytes(UTF8));
  }

  @RequestMapping(method = GET, produces = "application/json")
  public void getServiceDocument(HttpServletResponse response) throws IOException {
    String content = IOUtils.toString(new ClassPathResource("index.hal.json").getInputStream());
    String contentFinal = placeholderReplacer.replacePlaceholder(content);
    response.setContentType("application/json");
    response.getOutputStream().write(contentFinal.getBytes(UTF8));
  }
}
