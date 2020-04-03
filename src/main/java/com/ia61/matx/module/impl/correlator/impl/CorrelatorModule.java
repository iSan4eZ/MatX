package com.ia61.matx.module.impl.correlator.impl;

import com.ia61.matx.model.input.impl.DualInput;
import com.ia61.matx.model.output.impl.SingleOutput;
import com.ia61.matx.model.ui.FieldType;
import com.ia61.matx.model.ui.PopupField;
import com.ia61.matx.module.impl.correlator.Correlator;
import com.ia61.matx.module.impl.interrupter.Interruptable;

import java.util.Collections;
import java.util.List;

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
    return "Коррелятор";
  }

  @Override
  public void interrupt() {
    currentValue = 0f;
  }

  @Override
  public List<PopupField> getPopupFields() {
    return Collections.singletonList(
        new PopupField<>(FieldType.LABEL, null, null,
            "Коррелятор сигналов. Осуществляющет накопительное суммирование (интегрирование) произведения двух входящих сигналов. Прерывается Тактовым Генератором"));
  }
}
