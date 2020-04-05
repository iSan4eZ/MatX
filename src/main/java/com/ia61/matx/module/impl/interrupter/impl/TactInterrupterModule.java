package com.ia61.matx.module.impl.interrupter.impl;

import com.ia61.matx.model.ui.FieldType;
import com.ia61.matx.model.ui.PopupField;
import com.ia61.matx.module.impl.interrupter.AbstractInterrupter;

import java.util.Arrays;
import java.util.List;

public class TactInterrupterModule extends AbstractInterrupter {

  @Override
  public String getModuleName() {
    return "Тактовий генератор";
  }

  @Override
  public Float getDataToFirstOutput(Long timestamp) {
    return 0f;
  }

  @Override
  public List<PopupField<?>> getPopupFields() {
    return Arrays.asList(
        new PopupField<>(FieldType.LABEL, null, null,
            "Cкидає значення кореляторів, до яких під'єднаний виход тактового генератору, на 0 з вказаною частотою."),
        new PopupField<>(FieldType.LONG, this::getInterruptFrequency, this::setInterruptFrequency,
            "Частота переривання (Гц):")
    );
  }
}
