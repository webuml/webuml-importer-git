package com.webuml.importer.git.importprocess;

import java.io.Serializable;

public class UserPreferences implements Serializable {

  public transient static final int MIN_WIDTH = 320;
  public transient static final int MIN_HEIGHT = 320;
  public transient static final int MAX_WIDTH = 4096;
  public transient static final int MAX_HEIGHT = 4096;

  private int windowWidth;
  private int windowHeight;

  public int getWindowWidth() {
    return windowWidth;
  }

  public void setWindowWidth(int windowWidth) {
    this.windowWidth = windowWidth;
  }

  public int getWindowHeight() {
    return windowHeight;
  }

  public void setWindowHeight(int windowHeight) {
    this.windowHeight = windowHeight;
  }
}
