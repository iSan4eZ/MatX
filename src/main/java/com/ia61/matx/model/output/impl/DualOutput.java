package com.ia61.matx.model.output.impl;

import com.ia61.matx.model.output.Output;

public interface DualOutput<TYPE> {

  TYPE getDataToFirstOutput();

  TYPE getDataToSecondOutput();

  default Output<TYPE> getFirstOutput() {
    return this::getDataToFirstOutput;
  }

  default Output<TYPE> getSecondOutput() {
    return this::getDataToSecondOutput;
  }
}
