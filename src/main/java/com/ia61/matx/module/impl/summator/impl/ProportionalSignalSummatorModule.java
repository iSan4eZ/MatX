package com.ia61.matx.module.impl.summator.impl;

import com.ia61.matx.model.input.impl.DualInput;
import com.ia61.matx.model.output.impl.SingleOutput;
import com.ia61.matx.module.impl.summator.Summator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProportionalSignalSummatorModule extends DualInput implements Summator, SingleOutput {

  private Float firstSignalCoefficient = 1f;
  private Float secondSignalCoefficient = 1f;


  @Override
  public Float getDataToFirstOutput(Long timestamp) {
    Float firstValue = getFirstInput().requestData(timestamp) * firstSignalCoefficient;
    Float secondValue = getSecondInput().requestData(timestamp) * secondSignalCoefficient;
    return firstValue + secondValue;
  }

  @Override
  public String getModuleName() {
    return "Proportional Signal Summator";
  }
}
