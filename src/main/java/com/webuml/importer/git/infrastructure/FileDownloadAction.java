package com.webuml.importer.git.infrastructure;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class FileDownloadAction {

  public byte[] download(URI urlString) throws IOException {
    try (InputStream in = urlString.toURL().openStream();
         ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      final byte dataBlock[] = new byte[8192];
      int count;
      while ((count = in.read(dataBlock, 0, dataBlock.length)) != -1) {
        baos.write(dataBlock, 0, count);
      }
      return baos.toByteArray();
    }
  }
}
