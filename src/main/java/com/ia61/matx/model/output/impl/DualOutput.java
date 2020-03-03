package com.ia61.matx.model.output.impl;

import com.ia61.matx.model.output.Output;

public interface DualOutput {

  Float getDataToFirstOutput(Long timestamp);

  Float getDataToSecondOutput(Long timestamp);

  default Output getFirstOutput() {
    return this::getDataToFirstOutput;
  }

  default Output getSecondOutput() {
    return this::getDataToSecondOutput;
  }
}
