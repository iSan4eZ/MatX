package com.ia61.matx.util;

import java.util.Arrays;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberUtilTest {

  @Test
  public void shouldFindGCD() {
    assertThat(NumberUtil.findGCD(Arrays.asList(24L, 18L))).isEqualTo(6L);
    assertThat(NumberUtil.findGCD(Arrays.asList(10L, 30L))).isEqualTo(10L);
    assertThat(NumberUtil.findGCD(Arrays.asList(10L, 20L, 25L))).isEqualTo(5L);
    assertThat(NumberUtil.findGCD(Arrays.asList(27L, 51L, 81L))).isEqualTo(3L);
  }
}
