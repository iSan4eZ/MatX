package com.ia61.matx.module.impl.interrupter;

import com.ia61.matx.module.impl.correlator.impl.CorrelatorModule;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public abstract class AbstractInterrupter<MODULE extends Interruptable> implements Interrupter {

  private Long interruptFrequency = 1000L;
  private List<MODULE> correlatorModuleList;

  public void interruptCorrelators() {
    correlatorModuleList.forEach(MODULE::interrupt);
  }

}
