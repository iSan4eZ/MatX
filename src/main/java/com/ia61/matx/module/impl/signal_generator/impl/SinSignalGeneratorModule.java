package com.ia61.matx.module.impl.signal_generator.impl;

import com.ia61.matx.model.ui.PopupField;
import com.ia61.matx.model.ui.enums.FieldType;
import com.ia61.matx.module.impl.signal_generator.AbstractSignalGeneratorModule;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class SinSignalGeneratorModule extends AbstractSignalGeneratorModule {

  private Float height = 1f;
  private Integer periodsPerSymbol = 1;
  private Float offset = 0f;
  //Hz - amount of periods per second
  private Float frequency = 3f;
  private Boolean repeatable = false;
  private Long timeOffset = 0L;

  private String symbol = "";

  @Override
  public Float getDataToFirstOutput(Long timestamp) {
    final long timestampWithOffset = timestamp - timeOffset;
    if (timestampWithOffset < 0) {
      return 0f;
    }
    int coefficient = getCurrentCoefficient(timestampWithOffset, frequency, repeatable, symbol);
    return (((float) Math.sin((Math.PI * timestampWithOffset) / getHalfInterval(frequency, periodsPerSymbol)) * height)
        * coefficient) + offset;
  }

  @Override
  public String getModuleName() {
    return "Генератор гармонічного сигналу";
  }

  @Override
  public List<PopupField<?>> getPopupFields() {
    return Arrays.asList(
        new PopupField<>(FieldType.LABEL, null, null,
            "Передає символ гармонічним сигналом."),
        new PopupField<>(FieldType.INTEGER, this::getPeriodsPerSymbol, this::setPeriodsPerSymbol, "Періоди на символ:"),
        new PopupField<>(FieldType.FLOAT, this::getFrequency, this::setFrequency, "Частота (Гц):"),
        new PopupField<>(FieldType.FLOAT, this::getHeight, this::setHeight, "Амплітуда:"),
        new PopupField<>(FieldType.BOOLEAN, this::getRepeatable, this::setRepeatable, "Циклічний:"),
        new PopupField<>(FieldType.BINARY_STRING, this::getSymbol, this::setSymbol, "Символ:"),
        new PopupField<>(FieldType.LONG, this::getTimeOffset, this::setTimeOffset, "Затримка (мс):")
    );
  }
}
