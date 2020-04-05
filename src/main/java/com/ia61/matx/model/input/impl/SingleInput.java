package com.ia61.matx.model.input.impl;

import com.ia61.matx.model.input.Input;
import com.ia61.matx.model.input.InputConnection;
import com.ia61.matx.module.Module;
import lombok.Getter;

import java.util.Optional;

@Getter
public abstract class SingleInput implements Input {

  private InputConnection firstInput;

  public Boolean connectFirstInput(InputConnection inputConnection) {
    firstInput = inputConnection;
    return true;
  }

  @Override
  public int getInputCount() {
    return 1;
  }

  public Boolean connectToInput(Module module, Integer outputNumber, Integer inputNumber) {
    final Optional<InputConnection> optionalInputConnection = getInputConnection(module, outputNumber);
    if (!optionalInputConnection.isPresent()) {
      return false;
    }
    final InputConnection inputConnection = optionalInputConnection.get();
    if (inputNumber == 0) {
      return connectFirstInput(inputConnection);
    } else {
      return false;
    }
  }

  public void disconnectFromInput(Integer inputNumber) {
    if (inputNumber == 0) {
      firstInput = null;
    }
  }

}
