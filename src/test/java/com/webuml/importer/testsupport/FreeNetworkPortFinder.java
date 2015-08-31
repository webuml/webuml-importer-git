package com.webuml.importer.testsupport;

import java.net.ServerSocket;

public class FreeNetworkPortFinder {

  public static int findFreePort() {
    try (ServerSocket serverSocket = new ServerSocket(0)) {
      return serverSocket.getLocalPort();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
