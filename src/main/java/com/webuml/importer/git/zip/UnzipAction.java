package com.webuml.importer.git.zip;

import com.webuml.importer.git.infrastructure.FileDownloadAction;
import com.webuml.importer.parser.VirtualFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipAction {

  private final static int BUFFER_SIZE = 2048;

  private Logger logger = LoggerFactory.getLogger(FileDownloadAction.class);

  public List<VirtualFile> unzipFiles(InputStream zippedFile) throws IOException {
    ZipInputStream inputStream = new ZipInputStream(zippedFile);
    ZipEntry entry;
    List<VirtualFile> files = new ArrayList<>();
    while ((entry = inputStream.getNextEntry()) != null) {
      if (!entry.isDirectory()) {
        logger.info("Extracting File from zip " + entry);
        int bytesRead;
        byte data[] = new byte[BUFFER_SIZE];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while ((bytesRead = inputStream.read(data, 0, BUFFER_SIZE)) != -1) {
          outputStream.write(data, 0, bytesRead);
        }
        outputStream.flush();
        outputStream.close();
        files.add(createVirtualFile(entry, outputStream));
      }
    }
    inputStream.close();
    return files;
  }

  private VirtualFile createVirtualFile(ZipEntry entry, ByteArrayOutputStream outputStream) {
    VirtualFile virtualFile = new VirtualFile();
    virtualFile.setFileStream(new ByteArrayInputStream(outputStream.toByteArray()));
    virtualFile.setFileName(entry.getName());
    return virtualFile;
  }
}
