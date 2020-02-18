package com.ia61.matx.model.output.impl;

import com.ia61.matx.model.output.Output;

public abstract class DualOutput<TYPE> {

  public abstract TYPE getDataToFirstOutput();

  public abstract TYPE getDataToSecondOutput();

  public Output<TYPE> getFirstOutput() {
    return this::getDataToFirstOutput;
  }

  public Output<TYPE> getSecondOutput() {
    return this::getDataToSecondOutput;
  }
}
