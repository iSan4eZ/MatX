package com.ia61.matx.module.impl.signal_generator.impl;

import com.ia61.matx.module.impl.signal_generator.AbstractSignalGeneratorModule;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SinSignalGeneratorModule extends AbstractSignalGeneratorModule {

  private Float height = 1f;
  private Integer periodsPerSymbol = 1;
  private Float offset = 0f;
  //Hz - amount of periods per second
  private Float frequency = 3f;
  private Long tactPerPeriod = 50L;
  private Boolean repeatable = false;

  private String symbol = "101";

  @Override
  public Float getDataToFirstOutput(Long timestamp) {
//    if (timestamp % getDiscretizationFrequency() != 0) {
//      return null;
//    }
    int coefficient = 0;

    int charIndex = (int) Math.floor(timestamp / (1000f / frequency * periodsPerSymbol));
    if (repeatable) {
      charIndex = charIndex % symbol.length();
    }
    if (symbol.length() >= charIndex + 1) {
      final char c = symbol.charAt(charIndex);
      switch (c) {
        case '1':
          coefficient = 1;
          break;
        case '0':
          coefficient = -1;
          break;
        default:
          coefficient = 0;
          break;
      }
    }
    return (((float) Math.sin((Math.PI * timestamp) / getHalfInterval()) * height) * coefficient) + offset;
  }

  private Float getHalfInterval() {
    return 1000f / frequency / 2f;
  }

  private Long getDiscretizationFrequency() {
    return (long) Math.max(1, 1000 / frequency / tactPerPeriod);
  }

  @Override
  public String getModuleName() {
    return "Sinus Signal Generator";
  }
}
