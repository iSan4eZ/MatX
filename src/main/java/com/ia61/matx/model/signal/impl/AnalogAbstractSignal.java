package com.ia61.matx.model.signal.impl;

import com.ia61.matx.model.signal.AbstractSignal;
import java.util.List;

public class AnalogAbstractSignal extends AbstractSignal {

  public AnalogAbstractSignal(List<Float> data, Long frequency) {
    super(data, frequency);
  }

  @Override
  public Float getValue(int positionInData, Float positionBetweenDataPoints) {
    final Float previousValue = data.get(positionInData);
    final Float nextValue = data.get(positionInData + 1);

    final float diff = Math.abs(previousValue - nextValue);

    final float diffAtPosition = diff * positionBetweenDataPoints;

    return Math.min(previousValue, nextValue) + diffAtPosition;
  }
}
