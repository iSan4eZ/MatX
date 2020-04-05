package com.ia61.matx.module.impl.interrupter;

import com.ia61.matx.model.input.impl.NoInput;
import com.ia61.matx.model.output.impl.SingleOutput;
import com.ia61.matx.service.GeneralProcessor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public abstract class AbstractInterrupter extends NoInput implements Interrupter,
    SingleOutput {

  private Long interruptFrequency = 3L;
  private List<Interruptable> interruptableModuleList = new ArrayList<>();

  public AbstractInterrupter() {
    GeneralProcessor.interrupterList.add(this);
  }

  public void addInterruptable(Interruptable interruptableModule) {
    interruptableModuleList.add(interruptableModule);
  }

  public void interruptAll(Long timestamp) {
    if (timestamp % (1000 / interruptFrequency) == 0) {
      interruptableModuleList.forEach(Interruptable::interrupt);
    }
  }

}
