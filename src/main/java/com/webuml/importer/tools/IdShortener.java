package com.webuml.importer.tools;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.IllegalFormatConversionException;

@Component
public class IdShortener {

  private static char[] ALPHABET = "23456789abcdefghjkmnopqrtuvwxyzABCDEFGHJKLMNPQRTUVWXYZ".toCharArray(); // not 1ilI 0O sS
  private static BigInteger ALPHABET_LEN = new BigInteger(Integer.toString(ALPHABET.length));

  public String shorten(BigInteger bigInteger) {
    if (bigInteger == null) return null;
    StringBuilder sb = new StringBuilder();
    do {
      BigInteger[] divideAndRemainder = bigInteger.divideAndRemainder(ALPHABET_LEN);
      bigInteger = divideAndRemainder[0];
      sb.append(ALPHABET[divideAndRemainder[1].intValue()]);
    } while (bigInteger.compareTo(BigInteger.ZERO) > 0);
    return sb.reverse().toString();
  }

  public BigInteger expand(String shortStr) {
    if (shortStr == null || shortStr.isEmpty()) return null;
    BigInteger bigInteger = BigInteger.ZERO;
    for (int i = 0; i < shortStr.length(); i++) {
      char c = shortStr.charAt(i);
      int exponent = (shortStr.length() - 1) - i;
      bigInteger = bigInteger.add(exponent == 0 ? pick(c) : ALPHABET_LEN.pow(exponent).multiply(pick(c)));
    }
    return bigInteger;
  }

  private static BigInteger pick(char c) {
    for (int i = 0; i < ALPHABET.length; i++) {
      if (c == ALPHABET[i]) {
        return new BigInteger(Integer.toString(i));
      }
    }
    throw new IllegalFormatConversionException(c, ShortId.class);
  }
}
