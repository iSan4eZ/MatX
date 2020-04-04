package com.ia61.matx.model.output.impl;

import com.ia61.matx.model.output.Output;
import com.ia61.matx.model.output.OutputConnection;

import java.util.Optional;

public interface NoOutput extends Output {

  default int getOutputCount() {
    return 0;
  }

  default Optional<OutputConnection> getOutput(Integer outputNumber) {
    return Optional.empty();
  }

}
