package com.ia61.matx.module.impl.signal_generator.impl;

import com.ia61.matx.module.impl.signal_generator.AbstractSignalGeneratorModule;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DigitalSignalGeneratorModule extends AbstractSignalGeneratorModule {

  private Float height = 1f;
  private Integer periodsPerSymbol = 1;
  private Float offset = 0f;
  //Hz - amount of periods per second
  private Float frequency = 3f;
  private Boolean repeatable = false;

  private String symbol = "101";

  @Override
  public Float getDataToFirstOutput(Long timestamp) {
    final int coefficient = getCurrentCoefficient(timestamp, frequency, repeatable, symbol);

    final float signalPart = getHalfInterval(frequency, periodsPerSymbol)
        .compareTo(timestamp % getInterval(frequency, periodsPerSymbol)) * height;

    return (signalPart * coefficient) + offset;
  }

  @Override
  public String getModuleName() {
    return "Digital Signal Generator";
  }
}
