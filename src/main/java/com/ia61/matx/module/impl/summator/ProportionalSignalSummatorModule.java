package com.ia61.matx.module.impl.summator;

import com.ia61.matx.model.input.impl.DualInput;
import com.ia61.matx.model.output.impl.SingleOutput;
import com.ia61.matx.model.signal.Signal;
import com.ia61.matx.model.signal.impl.AnalogAbstractSignal;
import com.ia61.matx.module.Module;
import com.ia61.matx.util.NumberUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProportionalSignalSummatorModule extends DualInput<Signal> implements Module, SingleOutput<Signal> {

  private Float firstSignalCoefficient = 1f;
  private Float secondSignalCoefficient = 1f;

  @Override
  public Signal getDataToFirstOutput() {
    final Signal firstSignal = getFirstInput().requestData();
    final Signal secondSignal = getSecondInput().requestData();

    final Long frequencyGcd = NumberUtil.findGCD(firstSignal.getFrequency(), secondSignal.getFrequency());
    final long maxLength = Math.max(firstSignal.getLength(), secondSignal.getLength());
    List<Float> data = new ArrayList<>();
    for (long i = 0; i <= maxLength; i += frequencyGcd) {
      final Float firstValue = firstSignal.getValueAtTimestamp(frequencyGcd * i) * firstSignalCoefficient;
      final Float secondValue = secondSignal.getValueAtTimestamp(frequencyGcd * i) * secondSignalCoefficient;
      data.add(firstValue + secondValue);
    }
    return new AnalogAbstractSignal(data, frequencyGcd);
  }

  @Override
  public String getModuleName() {
    return "Proportional Signal Summator";
  }
}
