package com.webuml.importer.parser;

import com.webuml.importer.domain.BackEndModelInstance;
import com.webuml.importer.domain.primitives.ProjectId;

import java.net.URI;
import java.util.List;

public interface SourceBasedModelCreator {

  public BackEndModelInstance createModel(List<VirtualFile> files, ProjectId projectId, URI url);
}
