package com.webuml.importer.projectmanagerclient;

import com.webuml.importer.domain.primitives.ElementId;
import com.webuml.importer.domain.primitives.PackageId;
import com.webuml.importer.domain.primitives.ProjectId;
import com.webuml.importer.domain.primitives.PropertyId;

public interface ProjectManagerHypermediaDocument {

  ProjectId createProjectId();

  PropertyId createPropertyId();

  ElementId createClassId(ProjectId projectId);

  ElementId createAssociationId(ProjectId projectId);

  PackageId createPackageId(ProjectId projectId);
}
