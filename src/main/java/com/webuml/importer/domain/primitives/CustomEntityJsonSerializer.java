package com.webuml.importer.domain.primitives;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CustomEntityJsonSerializer extends JsonSerializer<Identifier> {

  @Override
  public void serialize(Identifier value, JsonGenerator jgen,
                        SerializerProvider provider) throws IOException,
                                                            JsonProcessingException {
    jgen.writeString(value.getValue());
  }
}
