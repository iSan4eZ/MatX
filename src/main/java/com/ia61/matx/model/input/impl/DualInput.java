package com.ia61.matx.model.input.impl;

import com.ia61.matx.model.input.Input;
import com.ia61.matx.model.input.InputConnection;
import com.ia61.matx.module.Module;
import lombok.Getter;

import java.util.Optional;

@Getter
public abstract class DualInput implements Input {

  private InputConnection firstInput;
  private InputConnection secondInput;

  public Boolean connectFirstInput(InputConnection inputConnection) {
    firstInput = inputConnection;
    return true;
  }

  public Boolean connectSecondInput(InputConnection inputConnection) {
    secondInput = inputConnection;
    return true;
  }

  public int getInputCount() {
    return 2;
  }

  public Boolean connectToInput(Module module, Integer outputNumber, Integer inputNumber) {
    final Optional<InputConnection> optionalInputConnection = getInputConnection(module, outputNumber);
    if (!optionalInputConnection.isPresent()) {
      return false;
    }
    final InputConnection inputConnection = optionalInputConnection.get();
    switch (inputNumber) {
      case 0:
        return connectFirstInput(inputConnection);
      case 1:
        return connectSecondInput(inputConnection);
      default:
        return false;
    }
  }

  public void disconnectFromInput(Integer inputNumber) {
    switch (inputNumber) {
      case 0:
        firstInput = null;
        return;
      case 1:
        secondInput = null;
    }
  }
}
