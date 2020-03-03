package com.ia61.matx.module.impl.signal_generator.impl;

import com.ia61.matx.model.signal.Signal;
import com.ia61.matx.model.signal.impl.AnalogAbstractSignal;
import com.ia61.matx.module.impl.signal_generator.AbstractSignalGeneratorModule;
import java.util.ArrayList;
import java.util.List;

import com.ia61.matx.util.NumberUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoiseSignalGeneratorModule extends AbstractSignalGeneratorModule {

  public Float upperBound = 1f;
  public Float lowerBound = 0f;

  @Override
  public Float getDataToFirstOutput(Long timestamp) {
    return NumberUtil.random(lowerBound, upperBound);
  }

  @Override
  public String getModuleName() {
    return "Noise Signal Generator";
  }
}
