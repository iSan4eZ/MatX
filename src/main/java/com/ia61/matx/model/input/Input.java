package com.ia61.matx.model.input;

import com.ia61.matx.model.output.OutputConnection;
import com.ia61.matx.module.Module;

import java.util.Optional;

public interface Input {

  default Optional<InputConnection> getInputConnection(Module module, Integer outputNumber) {
    final Optional<OutputConnection> output = module.getOutput(outputNumber);
    return output.flatMap(outputConnection -> Optional.of(new InputConnection(module, outputConnection)));
  }

  int getInputCount();

  Boolean connectToInput(Module module, Integer outputNumber, Integer inputNumber);

  void disconnectFromInput(Integer inputNumber);

}
