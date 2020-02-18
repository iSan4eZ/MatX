package com.ia61.matx.util;

import java.util.Arrays;
import org.junit.Test;

public class NumberUtilTest {

  @Test
  public void findGCD() {
    assert NumberUtil.findGCD(Arrays.asList(24L, 18L)) == 6L;
    assert NumberUtil.findGCD(Arrays.asList(10L, 30L)) == 10L;
    assert NumberUtil.findGCD(Arrays.asList(10L, 20L, 25L)) == 5L;
    assert NumberUtil.findGCD(Arrays.asList(27L, 51L, 81L)) == 3L;
  }
}
