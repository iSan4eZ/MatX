package com.ia61.matx.util;

import java.util.List;

public class NumberUtil {

  /**
   * @return Returns Greatest Common Divisor e.g. For 18 and 24 it will be 6.
   */
  public static Long findGCD(List<Long> numbers) {
    final long min = min(numbers);
    long gcd = 1L;

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
   * @return Returns minumum out of all provided numbers.
   */
  public static Long min(List<Long> numbers) {
    return numbers.stream()
        .mapToLong(Long::longValue)
        .min()
        .orElse(0L);
  }

}
