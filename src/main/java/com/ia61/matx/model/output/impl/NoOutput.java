package com.ia61.matx.model.output.impl;

import com.ia61.matx.model.output.Output;

public interface NoOutput extends Output {

  default int getOutputCount() {
    return 0;
  }

}
