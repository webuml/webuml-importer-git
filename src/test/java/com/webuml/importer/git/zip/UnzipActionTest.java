package com.webuml.importer.git.zip;

import com.webuml.importer.parser.VirtualFile;
import org.springframework.core.io.ClassPathResource;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class UnzipActionTest {

  UnzipAction unzipAction;

  @BeforeMethod
  public void setUp() {
    unzipAction = new UnzipAction();
  }

  @Test
  public void unzipTest() throws Exception {
    ClassPathResource classPathResource = new ClassPathResource("MockInjector-master.zip");

    List<VirtualFile> unzip = unzipAction.unzipFiles(classPathResource.getInputStream());
    assertNotNull(unzip);
    assertEquals(unzip.size(), 26);
    assertNotNull(unzip.get(0).getFileName());
    assertNotNull(unzip.get(0).getFileStream());
  }
}
