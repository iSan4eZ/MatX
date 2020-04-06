package com.ia61.matx.module.impl.signal_generator;

import com.ia61.matx.model.input.impl.NoInput;
import com.ia61.matx.model.output.impl.SingleOutput;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractSignalGeneratorModule extends NoInput implements SignalGenerator, SingleOutput {

  protected static int getCurrentCoefficient(Long timestamp, Float frequency, Boolean repeatable, String symbol) {
    int coefficient = 0;

    int charIndex = (int) Math.floor(timestamp / (1000f / frequency));
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
    return coefficient;
  }

  protected static Double getInterval(Float frequency, Integer periodsPerSymbol) {
    return 1000.0 / (double) frequency / (double) periodsPerSymbol;
  }

  protected static Double getHalfInterval(Float frequency, Integer periodsPerSymbol) {
    return getInterval(frequency, periodsPerSymbol) / 2.0;
  }

}
