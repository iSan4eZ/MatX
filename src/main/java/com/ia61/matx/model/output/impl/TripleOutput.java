package com.ia61.matx.model.output.impl;

import com.ia61.matx.model.output.Output;

public interface TripleOutput {

  Float getDataToFirstOutput(Long timestamp);

  Float getDataToSecondOutput(Long timestamp);

  Float getDataToThirdOutput(Long timestamp);

  default Output getFirstOutput() {
    return this::getDataToFirstOutput;
  }

  default Output getSecondOutput() {
    return this::getDataToSecondOutput;
  }

  default Output getThirdOutput() {
    return this::getDataToThirdOutput;
  }
}
