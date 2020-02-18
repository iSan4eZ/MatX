package com.ia61.matx.model.signal;

import java.util.Map;

public interface Signal {

  /**
   * @param pullRate value in millis. Defines timerange between datapoints.
   * @param pullTime value in millis. Defines total timerange of data to be generated.
   */
  Map<Float, Float> getData(Long pullRate, Long pullTime);

  Float getValueAtTimestamp(Long timestamp);

  Float getValue(int positionInData, Float positionBetweenDataPoints);

  Long getFrequency();

  Long getLength();

}
