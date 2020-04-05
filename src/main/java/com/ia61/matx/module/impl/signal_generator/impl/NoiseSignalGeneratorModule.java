package com.ia61.matx.module.impl.signal_generator.impl;

import com.ia61.matx.model.ui.FieldType;
import com.ia61.matx.model.ui.PopupField;
import com.ia61.matx.module.impl.signal_generator.AbstractSignalGeneratorModule;
import com.ia61.matx.util.NumberUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class NoiseSignalGeneratorModule extends AbstractSignalGeneratorModule {

  public Float upperBound = 1f;
  public Float lowerBound = 0f;

  @Override
  public Float getDataToFirstOutput(Long timestamp) {
    return NumberUtil.random(lowerBound, upperBound);
  }

  @Override
  public String getModuleName() {
    return "Генератор завад";
  }

  @Override
  public List<PopupField<?>> getPopupFields() {
    return Arrays.asList(
        new PopupField<>(FieldType.LABEL, null, null,
            "Генерує псевдорандомні значення у вказаних границях."),
        new PopupField<>(FieldType.FLOAT, this::getLowerBound, this::setLowerBound, "Нижня границя:"),
        new PopupField<>(FieldType.FLOAT, this::getUpperBound, this::setUpperBound, "Верхня границя:")
    );
  }
}
