package com.ia61.matx.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class NumberUtilTest {
//============================GCD=====================================
  @Test
  public void shouldFindGCDWithUsualValues() {
    assertThat(NumberUtil.findGCD(Arrays.asList(24L, 18L))).isEqualTo(6L);
    assertThat(NumberUtil.findGCD(Arrays.asList(10L, 30L))).isEqualTo(10L);
    assertThat(NumberUtil.findGCD(Arrays.asList(10L, 20L, 25L))).isEqualTo(5L);
    assertThat(NumberUtil.findGCD(Arrays.asList(27L, 51L, 81L))).isEqualTo(3L);
    assertThat(NumberUtil.findGCD(Arrays.asList(29L, 51L, 81L))).isEqualTo(1L);
    assertThat(NumberUtil.findGCD(Collections.singletonList(29L))).isEqualTo(29L);

  }

  @Test
  public void shouldFindGCDWithNegativeValues() {
    //Given
    final List<Long> givenValues = Arrays.asList(-27L, -51L, -81L);
    final long expectedResult = 3L;

    //When
    final Long result = NumberUtil.findGCD(givenValues);

    //Then
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  public void shouldFindGCDWithZeroValue() {
    //Given
    final List<Long> givenValues = Arrays.asList(0L, 51L);
    final long expectedResult = 51L;

    //When
    final Long result = NumberUtil.findGCD(givenValues);

    //Then
    assertThat(result).isEqualTo(expectedResult);

  }
  @Test
  public void shouldFindGCDWithDifferent() {
    //Given
    final List<Long> givenValues = Arrays.asList(9L, -6L);
    final long expectedResult = 3L;

    //When
    final Long result = NumberUtil.findGCD(givenValues);

    //Then
    assertThat(result).isEqualTo(expectedResult);

  }

  @Test
  public void shouldThrowExceptionCaseValuesZeroOnly() {
    //Given
    final List<Long> givenValues = Arrays.asList(0L, 0L);

    //When
    assertThatThrownBy(() -> NumberUtil.findGCD(givenValues))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Provided collection can't contain zeros only.");
  }
//============================Min=====================================

  @Test
  public void shouldFindMinWithUsualValues() {
    //Given
    final List<Long> givenValues = Arrays.asList(-20L, 2L, 17L);
    final long expectedResult = -20L;

    //When
    final Long result = NumberUtil.min(givenValues);

    //Then
    assertThat(result).isEqualTo(expectedResult);
  }
  @Test
  public void shouldFindMinWithNegativeValues() {
    //Given
    final List<Long> givenValues = Arrays.asList(-20L, -200L, -7L);
    final long expectedResult = -200L;

    //When
    final Long result = NumberUtil.min(givenValues);

    //Then
    assertThat(result).isEqualTo(expectedResult);
  }
  @Test
  public void shouldFindMinWithNull() {
    //Given
    final List<Long> givenValues = null;

    //When

    //Then
    //assertThat(result).isEqualTo(expectedResult);
    assertThatThrownBy(() -> NumberUtil.min(givenValues))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Provided collection can't be null or empty.");
  }
  @Test
  public void shouldFindMinWithListOfNull() {
    //Given
    final List<Long> givenValues = Collections.singletonList(null);
    //givenValues.add(null);
    //Then
    assertThatThrownBy(() -> NumberUtil.min(givenValues))
        .isInstanceOf(ArithmeticException.class)
        .hasMessage("Can't find minimum value of non-empty collection");
  }
  @Test
  public void shouldFindMinWithSameMins() {
    //Given
    final List<Long> givenValues = Arrays.asList(-20L, -20L);
    final long expectedResult = -20L;

    //When
    final Long result = NumberUtil.min(givenValues);

    //Then
    assertThat(result).isEqualTo(expectedResult);
  }
  @Test
  public void shouldFindMinWithSingleValue() {
    //Given
    final List<Long> givenValues = Collections.singletonList(-20L);
    final long expectedResult = -20L;

    //When
    final Long result = NumberUtil.min(givenValues);

    //Then
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  public void shouldFindMinWithoutValues() {
    //Given
    final List<Long> givenValues = Arrays.asList();
    //Then
    assertThatThrownBy(() -> NumberUtil.min(givenValues))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Provided collection can't be null or empty.");
  }
//============================Random=====================================

  @Test
  public void shouldThrowBoundException(){
    //Given
    final Float givenUpperBound = 0F;
    final Float givenLowerBound = 2.5F;
    //When
    assertThatThrownBy(() -> NumberUtil.random(givenLowerBound, givenUpperBound))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("UpperBound must be greater than LowerBound");
  }

  @Test
  public void shouldRandomWorks(){
    //Given
    final Float givenUpperBound = 15F;
    final Float givenLowerBound = 3F;
    //When
    final float result = NumberUtil.random(givenLowerBound, givenUpperBound);
    //Then
    assertThat(result).isBetween(givenLowerBound, givenUpperBound);

  }

}

