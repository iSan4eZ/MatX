package com.ia61.matx.model.input.impl;

import com.ia61.matx.model.input.Input;
import com.ia61.matx.model.input.InputConnection;
import com.ia61.matx.module.Module;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public abstract class MultiInput implements Input {

  private List<InputConnection> inputList = new ArrayList<>();

  public Boolean connectInput(InputConnection inputConnection, Integer inputNumber) {
    inputList.add(inputNumber, inputConnection);
    return true;
  }

  public int getInputCount() {
    return -1;
  }

  public Boolean connectToInput(Module module, Integer outputNumber, Integer inputNumber) {
    final Optional<InputConnection> optionalInputConnection = getInputConnection(module, outputNumber);
    if (!optionalInputConnection.isPresent()) {
      return false;
    }
    final InputConnection inputConnection = optionalInputConnection.get();
    return connectInput(inputConnection, inputNumber);
  }

  public void disconnectFromInput(Integer inputNumber) {
    inputList.remove((int) inputNumber);
  }

}
