package com.webuml.importer.git;

import com.webuml.importer.Application;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class IntegrationTestWithMongoDB extends AbstractTestNGSpringContextTests {

  @Value("${mongoPort}")
  private int MONGO_PORT;
  private MongodExecutable mongodExe;
  private MongodProcess mongod;

  @BeforeClass
  public void setup() throws Exception {
    MongodStarter runtime = MongodStarter.getDefaultInstance();

    IMongodConfig mongodConfig = new MongodConfigBuilder()
        .version(Version.Main.PRODUCTION)
        .net(new Net(MONGO_PORT, Network.localhostIsIPv6()))
        .build();

    mongodExe = runtime.prepare(mongodConfig);
    mongod = mongodExe.start();
  }

  @AfterClass
  public void teardown() throws Exception {
    if (mongod != null) {
      mongod.stop();
      mongodExe.stop();
    }
  }
}