package com.ia61.matx.model.output.impl;

import com.ia61.matx.model.output.Output;

public interface SingleOutput {

  Float getDataToFirstOutput(Long timestamp);

  default Output getFirstOutput() {
    return this::getDataToFirstOutput;
  }

}
