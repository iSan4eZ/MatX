package com.ia61.matx.module.impl.decision_maker.impl;

import com.ia61.matx.model.input.InputConnection;
import com.ia61.matx.model.ui.FieldType;
import com.ia61.matx.model.ui.PopupField;
import com.ia61.matx.module.impl.decision_maker.AbstractDecisionMakerModule;
import com.ia61.matx.module.impl.interrupter.AbstractInterrupter;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class DecisionMakerModule extends AbstractDecisionMakerModule {

  private Boolean showUnacceptedSymbols = false;
  private Float minimalAllowance = 0.4f;
  private Long valuesReceived = 0L;
  private Float previousValue = 0f;
  private Long h = 0L;

  private String resultSymbol = "";

  @Override
  public void calculateSymbolValues(Long timestamp) {
    final Float currentValue = getFirstInput().requestData(timestamp);
    h += currentValue.compareTo(previousValue);
    previousValue = currentValue;
    valuesReceived++;
  }

  //Symbol calculation
  @Override
  public void interrupt() {
    if (valuesReceived > 0) {
      final float resultValue = h / (float) valuesReceived;
      if (resultValue >= minimalAllowance) {
        resultSymbol += "1";
      } else if (resultValue <= minimalAllowance * (-1)) {
        resultSymbol += "0";
      } else if (showUnacceptedSymbols) {
        resultSymbol += "?";
      }
    }
    valuesReceived = 0L;
    previousValue = 0f;
    h = 0L;
  }

  @Override
  public Boolean connectSecondInput(InputConnection inputConnection) {
    //Second input is for interrupter. Otherwise won't connect
    if (AbstractInterrupter.class.isAssignableFrom(inputConnection.getConnectedModule().getClass())) {
      final AbstractInterrupter connectedInterrupter = (AbstractInterrupter) inputConnection.getConnectedModule();
      connectedInterrupter.addInterruptable(this);
      super.connectSecondInput(inputConnection);
      return true;
    }
    return false;
  }

  @Override
  public String getModuleName() {
    return "Модуль прийняття рішення";
  }

  @Override
  public List<PopupField<?>> getPopupFields() {
    return Arrays.asList(
        new PopupField<>(FieldType.BOOLEAN, this::getShowUnacceptedSymbols, this::setShowUnacceptedSymbols,
            "Показывать '?' в случае, если символ не принят:"),
        new PopupField<>(FieldType.FLOAT, this::getMinimalAllowance, this::setMinimalAllowance,
            "Нижний порог принятия символа (например, 0.4 для 40%):"),
        new PopupField<>(FieldType.LABEL, this::getResultSymbol, null, "Принятый сигнал:"));
  }
}
