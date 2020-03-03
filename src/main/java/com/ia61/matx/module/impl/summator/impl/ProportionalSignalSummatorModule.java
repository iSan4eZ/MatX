package com.ia61.matx.module.impl.summator.impl;

import com.ia61.matx.model.input.impl.DualInput;
import com.ia61.matx.model.output.impl.SingleOutput;
import com.ia61.matx.model.signal.Signal;
import com.ia61.matx.model.signal.impl.AnalogAbstractSignal;
import com.ia61.matx.module.Module;
import com.ia61.matx.module.impl.summator.Summator;
import com.ia61.matx.util.NumberUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
