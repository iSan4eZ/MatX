package com.ia61.matx.module.impl.correlator.impl;

import com.ia61.matx.model.input.impl.DualInput;
import com.ia61.matx.model.output.impl.SingleOutput;
import com.ia61.matx.model.signal.Signal;
import com.ia61.matx.model.signal.impl.AnalogAbstractSignal;
import com.ia61.matx.module.impl.correlator.Correlator;
import com.ia61.matx.module.impl.interrupter.Interruptable;
import com.ia61.matx.util.NumberUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CorrelatorModule extends DualInput<Signal> implements Correlator, SingleOutput<Signal>, Interruptable {

  private Float currentValue = 0f;

  @Override
  public Signal getDataToFirstOutput() {
    interrupt();
    final List<Signal> allSignals = Arrays.asList(getFirstInput().requestData(), getSecondInput().requestData());

    final List<Long> allFrequencies = allSignals.stream()
        .map(Signal::getFrequency)
        .collect(Collectors.toList());

    final long maxLength = allSignals.stream()
        .mapToLong(Signal::getLength)
        .max()
        .orElse(0L);

    final Long frequencyGcd = NumberUtil.findGCD(allFrequencies);
    List<Float> data = new ArrayList<>();
    for (long i = 0; i <= maxLength; i += frequencyGcd) {
      long finalI = i;
      currentValue += (float) allSignals.stream()
          .mapToDouble(signal -> signal.getValueAtTimestamp(finalI))
          .reduce(0, (a, b) -> a * b);
      data.add(currentValue);
    }
    return new AnalogAbstractSignal(data, frequencyGcd);
  }

  @Override
  public String getModuleName() {
    return "Correlator";
  }

  @Override
  public void interrupt() {
    currentValue = 0f;
  }
}
