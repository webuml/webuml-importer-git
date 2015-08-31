package com.webuml.importer.git.infrastructure;

import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HandlebarsConfiguration {

  @Value("${view.template.cache.active}")
  Boolean viewTemplateCacheActive;

  @Bean
  public HandlebarsViewResolver viewResolver() {
    final HandlebarsViewResolver viewResolver = new HandlebarsViewResolver();
    viewResolver.setCache(viewTemplateCacheActive);
    viewResolver.setSuffix("");
    return viewResolver;
  }

}
