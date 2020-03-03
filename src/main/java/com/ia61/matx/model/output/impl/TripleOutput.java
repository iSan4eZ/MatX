package com.ia61.matx.model.output.impl;

import com.ia61.matx.model.output.Output;
import com.ia61.matx.model.output.OutputConnection;

public interface TripleOutput extends Output {

  Float getDataToFirstOutput(Long timestamp);

  Float getDataToSecondOutput(Long timestamp);

  Float getDataToThirdOutput(Long timestamp);

  default int getOutputCount() {
    return 3;
  }

  default OutputConnection getFirstOutput() {
    return this::getDataToFirstOutput;
  }

  default OutputConnection getSecondOutput() {
    return this::getDataToSecondOutput;
  }

  default OutputConnection getThirdOutput() {
    return this::getDataToThirdOutput;
  }
}
