package com.webuml.importer.git.controller;

import com.webuml.importer.git.importprocess.UserPreferences;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.webuml.importer.git.importprocess.UserPreferences.*;
import static org.fest.assertions.Assertions.assertThat;
import static org.testng.Assert.*;

public class ImportHttpControllerTest {

  private ImportHttpController controller;

  @BeforeMethod
  public void setUp() throws Exception {
    controller = new ImportHttpController();
  }

  @Test
  public void happy_path() {
    UserPreferences preferences = controller.createUserPreferences("1000", "2000");
    assertThat(preferences.getWindowWidth()).isEqualTo(1000);
    assertThat(preferences.getWindowHeight()).isEqualTo(2000);
  }

  @Test
  public void minimal_values_are_set() {
    UserPreferences preferences = controller.createUserPreferences("-1", "0");
    assertThat(preferences.getWindowWidth()).isEqualTo(MIN_WIDTH);
    assertThat(preferences.getWindowHeight()).isEqualTo(MIN_HEIGHT);
  }

  @Test
  public void maximal_values_are_set() {
    UserPreferences preferences = controller.createUserPreferences("99999", "9999");
    assertThat(preferences.getWindowWidth()).isEqualTo(MAX_WIDTH);
    assertThat(preferences.getWindowHeight()).isEqualTo(MAX_HEIGHT);
  }

  @Test
  public void characters_are_ignored() {
    UserPreferences preferences = controller.createUserPreferences("a", "###");
    assertThat(preferences).isNull();
  }

}