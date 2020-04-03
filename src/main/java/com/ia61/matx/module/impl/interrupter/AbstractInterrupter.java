package com.ia61.matx.module.impl.interrupter;

import com.ia61.matx.model.input.impl.NoInput;
import com.ia61.matx.model.output.impl.NoOutput;
import com.ia61.matx.model.ui.PopupField;
import com.ia61.matx.service.GeneralProcessor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
public abstract class AbstractInterrupter<MODULE extends Interruptable> extends NoInput implements Interrupter,
    NoOutput {

  private Long interruptFrequency = 3L;
  private List<MODULE> interruptableModuleList = new ArrayList<>();

  public AbstractInterrupter() {
    GeneralProcessor.interrupterList.add(this);
  }

  public void addInterruptable(MODULE interruptableModule) {
    interruptableModuleList.add(interruptableModule);
  }

  public void interruptAll(Long timestamp) {
    if (timestamp % (1000 / interruptFrequency) == 0) {
      interruptableModuleList.forEach(MODULE::interrupt);
    }
  }

  @Override
  public List<PopupField> getPopupFields() {
    return Collections.emptyList();
  }

}
