package com.ia61.matx.model.signal.impl;

import com.ia61.matx.model.signal.AbstractSignal;
import java.util.List;

public class DiscreteAbstractSignal extends AbstractSignal {

  public DiscreteAbstractSignal(List<Float> data, Long frequency) {
    super(data, frequency);
  }

  @Override
  public Float getValue(int positionInData, Float positionBetweenDataPoints) {
    final Float previousPosition = data.get(positionInData);
    final Float nextPosition = data.get(positionInData + 1);

    if (positionBetweenDataPoints < 0.5) {
      return previousPosition;
    } else if (positionBetweenDataPoints == 0.5) {
      return (previousPosition + nextPosition) / 2;
    } else {
      return nextPosition;
    }
  }
}
