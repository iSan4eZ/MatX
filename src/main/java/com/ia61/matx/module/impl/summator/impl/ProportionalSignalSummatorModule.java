package com.ia61.matx.module.impl.summator.impl;

import com.ia61.matx.model.input.impl.DualInput;
import com.ia61.matx.model.output.impl.SingleOutput;
import com.ia61.matx.model.ui.FieldType;
import com.ia61.matx.model.ui.PopupField;
import com.ia61.matx.module.impl.summator.Summator;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
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
    return "Пропорциональный Сумматор Сигналов";
  }

  @Override
  public List<PopupField> getPopupFields() {
    return Arrays.asList(
        new PopupField<>(FieldType.LABEL, null, null,
            "Коеффициенты для пропорционального сумматора (K1*S1(t) + K2*S2(t))"),
        new PopupField<>(FieldType.FLOAT, this::getFirstSignalCoefficient, this::setFirstSignalCoefficient,
            "Коеффициент(K1) первого сигнала(S1(t)):"),
        new PopupField<>(FieldType.FLOAT, this::getSecondSignalCoefficient, this::setSecondSignalCoefficient,
            "Коеффициент(K2) первого сигнала(S2(t)):")
    );
  }
}
