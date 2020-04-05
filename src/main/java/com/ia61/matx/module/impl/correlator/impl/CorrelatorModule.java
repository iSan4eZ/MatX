package com.ia61.matx.module.impl.correlator.impl;

import com.ia61.matx.model.input.InputConnection;
import com.ia61.matx.model.input.impl.TripleInput;
import com.ia61.matx.model.output.impl.SingleOutput;
import com.ia61.matx.model.ui.FieldType;
import com.ia61.matx.model.ui.PopupField;
import com.ia61.matx.module.impl.correlator.Correlator;
import com.ia61.matx.module.impl.interrupter.AbstractInterrupter;
import com.ia61.matx.module.impl.interrupter.Interruptable;
import com.ia61.matx.util.ClassUtils;

import java.util.Collections;
import java.util.List;

public class CorrelatorModule extends TripleInput implements Correlator, SingleOutput, Interruptable {

  private Float currentValue = 0f;

  @Override
  public Float getDataToFirstOutput(Long timestamp) {
    Float firstValue = getFirstInput().requestData(timestamp);
    Float secondValue = getSecondInput().requestData(timestamp);
    currentValue += firstValue * secondValue;
    return currentValue;
  }

  @Override
  public Boolean connectThirdInput(InputConnection inputConnection) {
    //Third input is for interrupter. Otherwise won't connect
    if (ClassUtils.isAssignableFrom(inputConnection.getConnectedModule().getClass(), AbstractInterrupter.class)) {
      final AbstractInterrupter connectedInterrupter = (AbstractInterrupter) inputConnection.getConnectedModule();
      connectedInterrupter.addInterruptable(this);
      super.connectThirdInput(inputConnection);
      return true;
    }
    return false;
  }

  @Override
  public String getModuleName() {
    return "Корелятор";
  }

  @Override
  public void interrupt() {
    currentValue = 0f;
  }

  @Override
  public List<PopupField<?>> getPopupFields() {
    return Collections.singletonList(
        new PopupField<>(FieldType.LABEL, null, null,
            "Корелятор сигналів. "
                + "Що виконує накопичувальне суммування (інтегрування) добутку двох вхідних сигналів. "
                + "Переривається Тактовим генератором, котрий необхідно підключати на третій (нижній) вхід корелятора."));
  }
}
