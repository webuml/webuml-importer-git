package com.webuml.importer.parser;

import java.io.InputStream;

public class VirtualFile {

  InputStream fileStream;
  String fileName;

  public InputStream getFileStream() {
    return fileStream;
  }

  public void setFileStream(InputStream fileStream) {
    this.fileStream = fileStream;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
}
