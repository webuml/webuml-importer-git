package com.webuml.importer.git.infrastructure;

import com.webuml.importer.domain.primitives.ProjectId;
import com.webuml.importer.parser.VirtualFile;

import java.net.URI;
import java.util.List;

public interface ImportHandler {

  public ImportAbilityStatus canHandle(URI uri);

  public List<VirtualFile> create(URI uri, ProjectId projectId) throws Exception;
}
