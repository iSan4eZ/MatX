package com.ia61.matx.model.module.impl;

import com.ia61.matx.model.input.AbstractInput;
import com.ia61.matx.model.module.Module;
import com.ia61.matx.model.output.impl.SingleOutput;
import com.ia61.matx.model.signal.Signal;
import com.ia61.matx.model.signal.impl.AnalogAbstractSignal;
import com.ia61.matx.util.NumberUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SignalSummatorModule extends SingleOutput<Signal> implements Module {

  private List<AbstractInput<Signal>> inputList;

  public SignalSummatorModule(List<AbstractInput<Signal>> inputList) {
    this.inputList = inputList;
  }

  @Override
  public Signal getDataToFirstOutput() {
    final List<Signal> allSignals = inputList.stream()
        .map(AbstractInput::requestData)
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
          .mapToDouble(signal -> signal.getValueAtTimestamp(finalI * frequencyGcd))
          .sum();
      data.add(value);
    }
    return new AnalogAbstractSignal(data, frequencyGcd);
  }

}
