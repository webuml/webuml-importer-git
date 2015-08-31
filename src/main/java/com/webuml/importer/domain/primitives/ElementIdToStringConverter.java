package com.webuml.importer.domain.primitives;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ElementIdToStringConverter implements Converter<String, ElementId> {

  @Override
  public ElementId convert(String source) {
    return source == null ? null : new ElementId(source);
  }
}
