package com.ia61.matx.model.output.impl;

import com.ia61.matx.model.output.Output;
import com.ia61.matx.model.output.OutputConnection;

public interface DualOutput extends Output {

  Float getDataToFirstOutput(Long timestamp);

  Float getDataToSecondOutput(Long timestamp);

  default int getOutputCount() {
    return 2;
  }

  default OutputConnection getFirstOutput() {
    final OutputConnection getDataToFirstOutput = (OutputConnection) this::getDataToFirstOutput;
    return getDataToFirstOutput;
  }

  default OutputConnection getSecondOutput() {
    return this::getDataToSecondOutput;
  }
}
