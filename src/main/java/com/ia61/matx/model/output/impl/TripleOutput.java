package com.ia61.matx.model.output.impl;

import com.ia61.matx.model.output.Output;

public interface TripleOutput<TYPE> {

  TYPE getDataToFirstOutput();

  TYPE getDataToSecondOutput();

  TYPE getDataToThirdOutput();

  default Output<TYPE> getFirstOutput() {
    return this::getDataToFirstOutput;
  }

  default Output<TYPE> getSecondOutput() {
    return this::getDataToSecondOutput;
  }

  default Output<TYPE> getThirdOutput() {
    return this::getDataToThirdOutput;
  }
}
