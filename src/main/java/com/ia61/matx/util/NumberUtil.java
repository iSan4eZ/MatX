package com.ia61.matx.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class NumberUtil {

  public static Long findGCD(Long... numbers) {
    return findGCD(Arrays.asList(numbers));
  }

  /**
   * @return Returns Greatest Common Divisor e.g. For 18 and 24 it will be 6.
   * @throws IllegalArgumentException When provided collection is null, empty, or contains of zeros only.
   */
  public static Long findGCD(Collection<Long> numbers) {
    if (CollectionUtils.isEmpty(numbers)) {
      throw new IllegalArgumentException("Provided collection can't be null or empty.");
    }
    if (numbers.stream()
            .allMatch(number -> number == 0L)) {
      throw new IllegalArgumentException("Provided collection can't contain zeros only.");
    }

    numbers = numbers.stream()
            .filter(number -> number != 0)
            .collect(Collectors.toList());

    final long min = Math.abs(min(numbers));
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
   * @param numbers is list of numbers from which you'll get the smallest one.
   * @return Returns minimum out of all provided numbers.
   * @throws IllegalArgumentException When provided collection is null or empty.
   * @throws ArithmeticException      When provided collection isn't empty, but no min value was found.
   */
  public static Long min(Collection<Long> numbers) {
    if (CollectionUtils.isEmpty(numbers)) {
      throw new IllegalArgumentException("Provided collection can't be null or empty.");
    }
    return numbers.stream()
            .filter(Objects::nonNull)
            .mapToLong(Long::longValue)
            .min()
            .orElseThrow(() -> new ArithmeticException("Can't find minimum value of non-empty collection"));
  }

  /**
   * @param lowerBound represents lower bound of randomly generated numbers EXCLUDING the bound itself.
   * @param upperBound represents upper bound of randomly generated numbers EXCLUDING the bound itself.
   * @return Returns random number in provided bounds.
   * @throws IllegalArgumentException When lowerBound >= upperBound.
   */
  public static Float random(Float lowerBound, Float upperBound) {
    if (lowerBound >= upperBound) {
      throw new IllegalArgumentException("UpperBound must be greater than LowerBound");
    }

    final float diff = upperBound - lowerBound;
    return ((float) Math.random() * diff) + lowerBound;
  }

}
