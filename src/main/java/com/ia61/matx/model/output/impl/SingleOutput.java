package com.ia61.matx.model.output.impl;

import com.ia61.matx.model.output.Output;

public interface SingleOutput<TYPE> {

  TYPE getDataToFirstOutput();

  default Output<TYPE> getFirstOutput() {
    return this::getDataToFirstOutput;
  }

}
