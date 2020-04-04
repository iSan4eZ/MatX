package com.ia61.matx.module.impl.signal_generator.impl;

import com.ia61.matx.model.ui.FieldType;
import com.ia61.matx.model.ui.PopupField;
import com.ia61.matx.module.impl.signal_generator.AbstractSignalGeneratorModule;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class DigitalSignalGeneratorModule extends AbstractSignalGeneratorModule {

  private Float height = 1f;
  private Integer periodsPerSymbol = 1;
  private Float offset = 0f;
  //Hz - amount of periods per second
  private Float frequency = 3f;
  private Boolean repeatable = false;

  private String symbol = "101";

  @Override
  public Float getDataToFirstOutput(Long timestamp) {
    final int coefficient = getCurrentCoefficient(timestamp, frequency, repeatable, symbol);

    final float signalPart = getHalfInterval(frequency, periodsPerSymbol)
        .compareTo(timestamp % getInterval(frequency, periodsPerSymbol)) * height;

    return (signalPart * coefficient) + offset;
  }

  @Override
  public String getModuleName() {
    return "Генератор дискретного сигнала.";
  }

  @Override
  public List<PopupField<?>> getPopupFields() {
    return Arrays.asList(
        new PopupField<>(FieldType.INTEGER, this::getPeriodsPerSymbol, this::setPeriodsPerSymbol, "Периоды на символ:"),
        new PopupField<>(FieldType.FLOAT, this::getFrequency, this::setFrequency, "Частота (Гц):"),
        new PopupField<>(FieldType.FLOAT, this::getHeight, this::setHeight, "Амплитуда:"),
        new PopupField<>(FieldType.BOOLEAN, this::getRepeatable, this::setRepeatable, "Цикличный:"),
        new PopupField<>(FieldType.BINARY_STRING, this::getSymbol, this::setSymbol, "Символ:")
    );
  }
}
