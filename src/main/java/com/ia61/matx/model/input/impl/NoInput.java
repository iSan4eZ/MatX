package com.ia61.matx.model.input.impl;

import com.ia61.matx.model.input.Input;
import com.ia61.matx.model.output.OutputConnection;
import lombok.Getter;

@Getter
public abstract class NoInput implements Input {

  public int getInputCount() {
    return 0;
  }

  public Boolean connectToInput(OutputConnection output, Integer inputNumber) {
    return false;
  }

  public void disconnectFromInput(Integer inputNumber) {
  }
}
