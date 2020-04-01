package com.ia61.matx.model.output.impl;

import com.ia61.matx.model.output.Output;
import com.ia61.matx.model.output.OutputConnection;

import java.util.Optional;

public interface SingleOutput extends Output {

  Float getDataToFirstOutput(Long timestamp);

  default int getOutputCount() {
    return 1;
  }

  default OutputConnection getFirstOutput() {
    return this::getDataToFirstOutput;
  }

  default Optional<OutputConnection> getOutput(Integer outputNumber) {
    if (outputNumber == 0) {
      return Optional.of(getFirstOutput());
    }
    return Optional.empty();
  }

}
