package com.ia61.matx.model.input;

import com.ia61.matx.model.output.OutputConnection;
import com.ia61.matx.module.Module;
import com.ia61.matx.module.impl.interrupter.AbstractInterrupter;
import com.ia61.matx.module.impl.interrupter.Interruptable;
import com.ia61.matx.util.ClassUtils;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

public interface Input extends Serializable {

  default Optional<InputConnection> getInputConnection(Module module, Integer outputNumber) {
    final Optional<OutputConnection> output = module.getOutput(outputNumber);
    return output.flatMap(outputConnection -> Optional.of(new InputConnection(module, outputConnection)));
  }

  int getInputCount();

  Boolean connectToInput(Module module, Integer outputNumber, Integer inputNumber);

  void disconnectFromInput(Integer inputNumber);

  default void disconnectInternally(InputConnection inputConnection) {
    if (Objects.isNull(inputConnection)) {
      return;
    }
    final Module connectedModule = inputConnection.getConnectedModule();
    if (ClassUtils.isAssignableFrom(connectedModule.getClass(), AbstractInterrupter.class)) {
      ((AbstractInterrupter) connectedModule).removeInterruptable((Interruptable) this);
    }
  }

}
