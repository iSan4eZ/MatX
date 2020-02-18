package com.ia61.matx.model.output.impl;

import com.ia61.matx.model.output.Output;

public abstract class QuadOutput<TYPE> {

  public abstract TYPE getDataToFirstOutput();

  public abstract TYPE getDataToSecondOutput();

  public abstract TYPE getDataToThirdOutput();

  public abstract TYPE getDataToFourthOutput();

  public Output<TYPE> getFirstOutput() {
    return this::getDataToFirstOutput;
  }

  public Output<TYPE> getSecondOutput() {
    return this::getDataToSecondOutput;
  }

  public Output<TYPE> getThirdOutput() {
    return this::getDataToThirdOutput;
  }

  public Output<TYPE> getFouthOutput() {
    return this::getDataToFourthOutput;
  }
}
