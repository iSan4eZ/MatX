package com.ia61.matx.module.impl.summator.impl;

import com.ia61.matx.model.input.impl.MultiInput;
import com.ia61.matx.model.output.impl.SingleOutput;
import com.ia61.matx.model.ui.FieldType;
import com.ia61.matx.model.ui.PopupField;
import com.ia61.matx.module.impl.summator.Summator;

import java.util.Collections;
import java.util.List;

public class SignalSummatorModule extends MultiInput implements Summator, SingleOutput {

  @Override
  public Float getDataToFirstOutput(Long timestamp) {
    return (float) getInputList().stream()
        .mapToDouble(input -> input.requestData(timestamp))
        .sum();
  }

  @Override
  public String getModuleName() {
    return "Сумматор Сигналов";
  }

  @Override
  public List<PopupField> getPopupFields() {
    return Collections.singletonList(
        new PopupField<>(FieldType.LABEL, null, null, "Сумматор сигналов (S1 + S2 + .... + Sn)"));
  }
}
