package com.ia61.matx.util;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class NumberUtil {

  public static Long findGCD(Long... numbers){
    return findGCD(Arrays.asList(numbers));
  }

  /**
   * @return Returns Greatest Common Divisor e.g. For 18 and 24 it will be 6.
   */
  public static Long findGCD(List<Long> numbers) {
    if (CollectionUtils.isEmpty(numbers)) {
      throw new IllegalArgumentException("Provided collection can't be null or empty.");
    }

    final long min = Math.abs(min(numbers));
    long gcd = 1L;

    if (min < 1) {
      throw new IllegalArgumentException("Provided collection can't contain zeros only.");
    }

    for (int i = 1; i <= min; i++) {
      int finalI = i;
      if (numbers.stream()
          .allMatch(number -> number % finalI == 0)) {
        gcd = i;
      }
    }

    return gcd;
  }

  /**
   * @return Returns minimum out of all provided numbers.
   */
  public static Long min(List<Long> numbers) {
    return numbers.stream()
        .filter(Objects::nonNull)
        .mapToLong(Long::longValue)
        .min()
        .orElse(0L);
  }

  /**
   * @param lowerBound represents lower bound of randomly generated numbers EXCLUDING the bound itself.
   * @param upperBound represents upper bound of randomly generated numbers EXCLUDING the bound itself.
   * @return Returns random number in provided bounds.
   * @throws IllegalArgumentException When lowerBound >= upperBound.
   */
  public static Float random(Float lowerBound, Float upperBound){
    if (lowerBound >= upperBound) {
      throw new IllegalArgumentException("UpperBound must be greater than LowerBound");
    }

    final float diff = upperBound - lowerBound;
    return ((float) Math.random() * diff) + lowerBound;
  }

}
