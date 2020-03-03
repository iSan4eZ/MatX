package com.ia61.matx.module.impl.interrupter;

import com.ia61.matx.service.GeneralProcessor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public abstract class AbstractInterrupter<MODULE extends Interruptable> implements Interrupter {

  private Long interruptFrequency = 1000L;
  private List<MODULE> interruptableModuleList = new ArrayList<>();

  public AbstractInterrupter() {
    GeneralProcessor.interrupterList.add(this);
  }

  public void addInterruptable(MODULE interruptableModule) {
    interruptableModuleList.add(interruptableModule);
  }

  public void interruptAll(Long timestamp) {
    if (timestamp % interruptFrequency == 0) {
      interruptableModuleList.forEach(MODULE::interrupt);
    }
  }

}
