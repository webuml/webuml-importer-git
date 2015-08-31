package com.webuml.importer.git.repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
public class MongoConfiguration {

  private static final Logger LOG = Logger.getLogger(MongoConfiguration.class);

  @Value("${mongoUri}")
  String mongoUri;

  @Bean
  public MongoTemplate mongoTemplate() throws Exception {
    MongoClientURI clientUri = new MongoClientURI(mongoUri);
    LOG.info("Mongo Database: " + clientUri.getDatabase());
    SimpleMongoDbFactory dbFactory = new SimpleMongoDbFactory(new MongoClient(clientUri), clientUri.getDatabase());
    return new MongoTemplate(dbFactory);
  }
}
