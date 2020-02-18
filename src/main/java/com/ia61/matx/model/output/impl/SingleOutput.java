package com.ia61.matx.model.output.impl;

import com.ia61.matx.model.output.Output;

public abstract class SingleOutput<TYPE> {

  public abstract TYPE getDataToFirstOutput();

  public Output<TYPE> getFirstOutput() {
    return this::getDataToFirstOutput;
  }

}
