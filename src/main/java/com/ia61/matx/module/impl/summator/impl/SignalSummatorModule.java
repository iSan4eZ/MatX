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

public class SignalSummatorModule extends MultiInput implements Summator, SingleOutput {

  @Override
  public Float getDataToFirstOutput(Long timestamp) {
    return (float) getInputList().stream()
        .mapToDouble(input -> input.requestData(timestamp))
        .sum();
  }

  @Override
  public String getModuleName() {
    return "Signal Summator";
  }
}
