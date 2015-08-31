package com.webuml.importer.parser;

import com.webuml.importer.domain.BackEndModelInstance;
import com.webuml.importer.domain.primitives.PackageId;
import com.webuml.importer.domain.primitives.ProjectId;
import com.webuml.importer.domain.primitives.PropertyId;
import com.webuml.importer.projectmanagerclient.ProjectManagerHypermediaDocument;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Provider;
import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class SourceBasedModelCreatorTest {

  SourceBasedModelCreatorImpl sourceBasedModelCreator;

  @BeforeMethod
  public void setUp() throws Exception {
    sourceBasedModelCreator = new SourceBasedModelCreatorImpl();
    ElementModelCreator elementModelCreator = new ElementModelCreator();
    sourceBasedModelCreator.elementModelCreator = elementModelCreator;

    ProjectManagerHypermediaDocument projectManagerHypermediaDocument = mock(ProjectManagerHypermediaDocument.class);
    Provider providerMock = mock(Provider.class);
    when(providerMock.get()).thenReturn(projectManagerHypermediaDocument);
    elementModelCreator.setProjectManagerHypermediaDocument(providerMock);
    when(projectManagerHypermediaDocument.createClassId(any(ProjectId.class))).thenReturn(new PackageId(UUID.randomUUID().toString()));
    when(projectManagerHypermediaDocument.createAssociationId(any(ProjectId.class))).thenReturn(new PackageId(UUID.randomUUID().toString()));
    when(projectManagerHypermediaDocument.createPropertyId()).thenReturn(new PropertyId(UUID.randomUUID().toString()));
  }

  @Test
  public void testCreateClasses() throws Exception {
    String absolutePath = new File("").getAbsolutePath();
    String rootDirectory = absolutePath + "/src/test/resources/java-classes/";
    List<VirtualFile> files = new FileWalkerHelper().walkDirectory(new File(rootDirectory));

    BackEndModelInstance backEndModelInstance = sourceBasedModelCreator.createModel(files, new ProjectId(), URI.create("bla"));
    assertNotNull(backEndModelInstance);
    assertEquals(backEndModelInstance.getProject().getName(), "bla");
    assertNotNull(backEndModelInstance.getClasses());
    assertEquals(backEndModelInstance.getClasses().size(), 10);
  }
}
