package com.ia61.matx.model.output.impl;

import com.ia61.matx.model.output.Output;

public interface QuadOutput {

  Float getDataToFirstOutput(Long timestamp);

  Float getDataToSecondOutput(Long timestamp);

  Float getDataToThirdOutput(Long timestamp);

  Float getDataToFourthOutput(Long timestamp);

  default Output getFirstOutput() {
    return this::getDataToFirstOutput;
  }

  default Output getSecondOutput() {
    return this::getDataToSecondOutput;
  }

  default Output getThirdOutput() {
    return this::getDataToThirdOutput;
  }

  default Output getFouthOutput() {
    return this::getDataToFourthOutput;
  }
}
