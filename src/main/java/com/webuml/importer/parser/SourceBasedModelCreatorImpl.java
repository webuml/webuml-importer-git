package com.webuml.importer.parser;

import com.webuml.importer.domain.BackEndModelInstance;
import com.webuml.importer.domain.primitives.ProjectId;
import com.webuml.importer.parser.candidate.CandidateModel;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.net.URI;
import java.util.List;

@Component
public class SourceBasedModelCreatorImpl implements SourceBasedModelCreator {

  private static final Logger LOG = Logger.getLogger(SourceBasedModelCreatorImpl.class);

  @Inject
  CandidateModelCreator candidateModelCreator;
  @Inject
  ElementModelCreator elementModelCreator;

  public SourceBasedModelCreatorImpl() {
    candidateModelCreator = new CandidateModelCreator();
  }

  public BackEndModelInstance createModel(List<VirtualFile> files, ProjectId projectId, URI url) {
    CandidateModel candidateModel = candidateModelCreator.getCandidateModel(files);
    LOG.info("Candiate Model Created");
    BackEndModelInstance backEndModelInstance = elementModelCreator.createBackEndModelInstance(candidateModel, projectId, url);
    LOG.info("Element Model Created");
    return backEndModelInstance;
  }
}
