package com.webuml.importer.git.zip;

import com.webuml.importer.domain.primitives.ProjectId;
import com.webuml.importer.git.infrastructure.FileDownloadAction;
import com.webuml.importer.git.infrastructure.ImportAbilityStatus;
import com.webuml.importer.git.infrastructure.ImportHandler;
import com.webuml.importer.parser.VirtualFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import static com.webuml.importer.git.infrastructure.ImportAbilityStatus.NOT_SUPPORTED;
import static com.webuml.importer.git.infrastructure.ImportAbilityStatus.SUPPORTED;

@Component
public class ZipImportHandler implements ImportHandler {

  private Logger logger = LoggerFactory.getLogger(ZipImportHandler.class);

  String ZIP_MATCHER = ".+\\.zip";

  @Override
  public ImportAbilityStatus canHandle(URI uri) {
    String url = uri.toString();
    boolean matches = url.matches(ZIP_MATCHER);
    return matches ? SUPPORTED : NOT_SUPPORTED;
  }

  @Override
  public List<VirtualFile> create(URI uri, ProjectId projectId) throws IOException {
    FileDownloadAction fileDownloadAction = new FileDownloadAction();
    byte[] download = fileDownloadAction.download(uri);
    logger.info("downloaded " + download.length + " bytes");
    UnzipAction unzipAction = new UnzipAction();
    List<VirtualFile> virtualFiles = unzipAction.unzipFiles(new ByteArrayInputStream(download));
    logger.info("unzipped " + virtualFiles.size() + " files");
    return virtualFiles;
  }
}
