package com.ia61.matx.model.output.impl;

import com.ia61.matx.model.output.Output;
import com.ia61.matx.model.output.OutputConnection;

import java.util.Optional;

public interface DualOutput extends Output {

  Float getDataToFirstOutput(Long timestamp);

  Float getDataToSecondOutput(Long timestamp);

  default int getOutputCount() {
    return 2;
  }

  default OutputConnection getFirstOutput() {
    final OutputConnection getDataToFirstOutput = this::getDataToFirstOutput;
    return getDataToFirstOutput;
  }

  default OutputConnection getSecondOutput() {
    return this::getDataToSecondOutput;
  }

  default Optional<OutputConnection> getOutput(Integer outputNumber) {
    switch (outputNumber) {
      case 0:
        return Optional.of(getFirstOutput());
      case 1:
        return Optional.of(getSecondOutput());
      default:
        return Optional.empty();
    }
  }

}
