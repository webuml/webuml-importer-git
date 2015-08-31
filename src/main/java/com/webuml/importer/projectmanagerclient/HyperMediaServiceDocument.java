package com.webuml.importer.projectmanagerclient;

import com.theoryinpractise.halbuilder.api.ReadableRepresentation;
import com.theoryinpractise.halbuilder.json.JsonRepresentationFactory;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

class HyperMediaServiceDocument {

  private static final Charset UTF8 = Charset.forName("UTF-8");

  private final Logger logger = LoggerFactory.getLogger(HyperMediaServiceDocument.class);

  private final String url;
  private ReadableRepresentation representation;

  public HyperMediaServiceDocument(String url) {
    this.url = url;
  }

  public ReadableRepresentation fetch() {
    if (representation == null) {
      logger.info("fetching service document from Server " + url);
      Response response = JerseyClientBuilder.newClient()
          .register(JacksonFeature.class)
          .target(url)
          .request(APPLICATION_JSON_TYPE)
          .buildGet()
          .invoke();

      try (InputStream is = response.readEntity(InputStream.class)) {
        JsonRepresentationFactory representationFactory = new JsonRepresentationFactory();
        representation = representationFactory.readRepresentation(new InputStreamReader(is, UTF8));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    return representation;
  }

}
