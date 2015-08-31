package com.webuml.importer.tools;

import java.io.IOException;
import java.io.InputStream;

public class Toolbox {

  public static void silentClose(InputStream is) {
    if (is != null) {
      try {
        is.close();
      } catch (IOException e) {
        // ignore
      }
    }
  }
}
