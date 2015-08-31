package com.webuml.importer.tools;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigInteger;

import static org.fest.assertions.Assertions.assertThat;

public class IdShortenerTest {

  private IdShortener shortener;

  @BeforeMethod
  public void setUp() throws Exception {
    shortener = new IdShortener();
  }

  @DataProvider
  Object[][] integersAndShortNumbers() {
    return new Object[][]{
        {"0", "2"},
        {"1", "3"},
        {"2", "4"},
        {"51", "X"},
        {"52", "Y"},
        {"53", "Z"},
        {"54", "32"},
        {"107", "3Z"},
        {"108", "42"},
        {"25799783630164375744096776024", "6WwqhjB2TWJQuPNNM"},
    };
  }

  @DataProvider
  Object[][] shortNumbersAndIntegers() {
    Object[][] data = integersAndShortNumbers();
    for (Object[] objects : data) {
      Object tmp = objects[0];
      objects[0] = objects[1];
      objects[1] = tmp;
    }
    return data;
  }

  @Test(dataProvider = "integersAndShortNumbers")
  public void integer_to_ShortNumber_conversion(String intStr, String shortStr) {
    BigInteger bigInteger = new BigInteger(intStr);

    String id = shortener.shorten(bigInteger);

    assertThat(id).isEqualTo(shortStr);
  }

  @Test(dataProvider = "shortNumbersAndIntegers")
  public void integer_to_conversion(String shortStr, String intStr) {

    BigInteger bigInteger = shortener.expand(shortStr);

    assertThat(bigInteger).isEqualTo(new BigInteger(intStr));
  }

  @Test
  public void integer_to_ShortNumber_conversion__NULL() {
    String id = shortener.shorten(null);

    assertThat(id).isNull();
  }
}
