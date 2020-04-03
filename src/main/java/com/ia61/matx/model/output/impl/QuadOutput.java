package com.ia61.matx.model.output.impl;

import com.ia61.matx.model.output.Output;
import com.ia61.matx.model.output.OutputConnection;

import java.util.Optional;

public interface QuadOutput extends Output {

  Float getDataToFirstOutput(Long timestamp);

  Float getDataToSecondOutput(Long timestamp);

  Float getDataToThirdOutput(Long timestamp);

  Float getDataToFourthOutput(Long timestamp);

  default int getOutputCount() {
    return 4;
  }

  default OutputConnection getFirstOutput() {
    return this::getDataToFirstOutput;
  }

  default OutputConnection getSecondOutput() {
    return this::getDataToSecondOutput;
  }

  default OutputConnection getThirdOutput() {
    return this::getDataToThirdOutput;
  }

  default OutputConnection getFouthOutput() {
    return this::getDataToFourthOutput;
  }

  default Optional<OutputConnection> getOutput(Integer outputNumber) {
    switch (outputNumber) {
      case 0:
        return Optional.of(getFirstOutput());
      case 1:
        return Optional.of(getSecondOutput());
      case 2:
        return Optional.of(getThirdOutput());
      case 3:
        return Optional.of(getFouthOutput());
      default:
        return Optional.empty();
    }
  }
}
