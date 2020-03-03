package com.ia61.matx.module.impl.summator.impl;

import com.ia61.matx.model.input.InputConnection;
import com.ia61.matx.model.input.impl.MultiInput;
import com.ia61.matx.model.output.impl.SingleOutput;
import com.ia61.matx.model.signal.Signal;
import com.ia61.matx.model.signal.impl.AnalogAbstractSignal;
import com.ia61.matx.module.Module;
import com.ia61.matx.module.impl.summator.Summator;
import com.ia61.matx.util.NumberUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SignalSummatorModule extends MultiInput<Signal> implements Summator, SingleOutput<Signal> {

  @Override
  public Signal getDataToFirstOutput() {
    final List<Signal> allSignals = getInputList().stream()
        .map(InputConnection::requestData)
        .collect(Collectors.toList());

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
      final Float value = (float) allSignals.stream()
          .mapToDouble(signal -> signal.getValueAtTimestamp(finalI))
          .sum();
      data.add(value);
    }
    return new AnalogAbstractSignal(data, frequencyGcd);
  }

  @Override
  public String getModuleName() {
    return "Signal Summator";
  }
}
