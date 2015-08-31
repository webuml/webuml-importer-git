package com.webuml.importer.git.jgit;

import com.webuml.importer.git.infrastructure.ImportAbilityStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static com.webuml.importer.git.infrastructure.ImportAbilityStatus.NOT_SUPPORTED;
import static com.webuml.importer.git.infrastructure.ImportAbilityStatus.SUPPORTED;
import static org.testng.Assert.assertEquals;

public class GitImportHandlerTest {

  GitImportHandler importHandler;

  @BeforeMethod
  public void setUp() throws Exception {
    importHandler = new GitImportHandler();
  }

  @DataProvider(name = "zipMatcherProvider")
  public static Object[][] zipMatcherProvider() {
    return new Object[][] {
        {"git://github.com/hypoport/MockInjector/archive/master.git", SUPPORTED},
        {"https://github.com/hypoport/MockInjector/archive/master.git", SUPPORTED},
        {"http://github.com/hypoport/MockInjector/archive/master.git", SUPPORTED},
        {"ftp://ftp.bla.com/bla.git", SUPPORTED},

        {".git", NOT_SUPPORTED},
        {"http://github.com/hypoport/MockInjector/archive/master.gi", NOT_SUPPORTED},
        {"http://github.com/master", NOT_SUPPORTED},
    };
  }

  @Test(dataProvider = "zipMatcherProvider")
  public void testCanHandle(String url, ImportAbilityStatus expected) throws Exception {
    ImportAbilityStatus abilityStatus = importHandler.canHandle(new URI(url));
    assertEquals(abilityStatus, expected);
  }
}
