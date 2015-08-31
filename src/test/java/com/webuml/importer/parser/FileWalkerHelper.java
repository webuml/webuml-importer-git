package com.webuml.importer.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class FileWalkerHelper {

  public List<VirtualFile> walkDirectory(File baseDirectory) throws FileNotFoundException {
    List<VirtualFile> virtualFiles = new ArrayList<>();
    File[] files = baseDirectory.listFiles();
    for (File file : files) {
      if (file.isFile()) {
        virtualFiles.add(parseFile(file));
      }
      if (file.isDirectory()) {
        virtualFiles.addAll(walkDirectory(file));
      }
    }
    return virtualFiles;
  }

  private VirtualFile parseFile(File file) throws FileNotFoundException {
    VirtualFile virtualFile = new VirtualFile();
    virtualFile.setFileName(file.getName());
    virtualFile.setFileStream(new FileInputStream(file));
    return virtualFile;
  }
}
