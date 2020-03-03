package com.ia61.matx.model.input;

import com.ia61.matx.model.output.Output;

public class InputConnection {

  private Output connectedOutput;

  public InputConnection(Output connectedOutput) {
    this.connectedOutput = connectedOutput;
  }

  public Float requestData(Long timestamp) {
    return connectedOutput.gatherData(timestamp);
  }

}
