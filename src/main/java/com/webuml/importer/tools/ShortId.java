package com.webuml.importer.tools;

import java.math.BigInteger;

public class ShortId {

  private transient static final IdShortener SHORTENER = new IdShortener();

  private final String shortId;

  public ShortId(String shortId) {
    this.shortId = shortId;
  }

  public ShortId(BigInteger bigInteger) {
    this.shortId = SHORTENER.shorten(bigInteger);
  }

  @Override
  public String toString() {
    return shortId;
  }

  public BigInteger toBigInteger() {
    return SHORTENER.expand(this.shortId);
  }
}
