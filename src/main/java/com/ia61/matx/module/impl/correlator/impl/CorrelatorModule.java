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

public class CorrelatorModule extends DualInput implements Correlator, SingleOutput, Interruptable {

  private Float currentValue = 0f;


  @Override
  public Float getDataToFirstOutput(Long timestamp) {
    Float firstValue = getFirstInput().requestData(timestamp);
    Float secondValue = getSecondInput().requestData(timestamp);
    currentValue += firstValue * secondValue;
    return currentValue;
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
