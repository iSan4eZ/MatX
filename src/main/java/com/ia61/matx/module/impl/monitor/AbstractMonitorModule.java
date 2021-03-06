package com.ia61.matx.module.impl.monitor;

import com.ia61.matx.model.input.impl.MultiInput;
import com.ia61.matx.model.output.impl.NoOutput;
import com.ia61.matx.model.ui.PopupField;
import com.ia61.matx.model.ui.enums.FieldType;
import com.ia61.matx.service.GeneralProcessor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;

@Getter
@Setter
public abstract class AbstractMonitorModule extends MultiInput implements Monitor, NoOutput {

  //frequency of data points in Hz
  private Long pullRate = 100L;

  //cached result
  private List<SortedMap<Long, Float>> result = new ArrayList<>();

  public AbstractMonitorModule() {
    GeneralProcessor.monitorList.add(this);
  }

  public abstract void gatherAllInputs(Long timestamp);

  public void resetResult() {
    result.clear();
  }

  @Override
  public List<PopupField<?>> getPopupFields() {
    return Arrays.asList(
        new PopupField<>(FieldType.LABEL, null, null,
            "Відображає результат симуляції блоків, що до нього під’єднані. "
                + "Відкрийте вікно повторно для оновлення графіку після симуляції."),
        new PopupField<>(FieldType.GRAPH, this::getResult, null, ""),
        new PopupField<>(FieldType.LONG, this::getPullRate, this::setPullRate, "Частота дискретизації (Гц):"));
  }

}
