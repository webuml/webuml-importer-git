package com.webuml.importer.git.zip;

import com.webuml.importer.git.infrastructure.ImportAbilityStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;

import static com.webuml.importer.git.infrastructure.ImportAbilityStatus.NOT_SUPPORTED;
import static com.webuml.importer.git.infrastructure.ImportAbilityStatus.SUPPORTED;
import static org.testng.Assert.assertEquals;

public class ZipImportHandlerTest {

  ZipImportHandler zipImportHandler;

  @BeforeMethod
  public void setUp() throws Exception {
    zipImportHandler = new ZipImportHandler();
  }

  @DataProvider(name = "zipMatcherProvider")
  public static Object[][] zipMatcherProvider() {
    return new Object[][] {
        {"https://github.com/hypoport/MockInjector/archive/master.zip", SUPPORTED},
        {"http://github.com/hypoport/MockInjector/archive/master.zip", SUPPORTED},
        {"ftp://ftp.bla.com/bla.zip", SUPPORTED},

        {".zip", NOT_SUPPORTED},
        {"http://github.com/hypoport/MockInjector/archive/master.zi", NOT_SUPPORTED},
        {"http://github.com/master", NOT_SUPPORTED},
    };
  }

  @Test(dataProvider = "zipMatcherProvider")
  public void testCanHandle(String url, ImportAbilityStatus expected) throws Exception {
    ImportAbilityStatus status = zipImportHandler.canHandle(new URI(url));
    assertEquals(status, expected);
  }
}
