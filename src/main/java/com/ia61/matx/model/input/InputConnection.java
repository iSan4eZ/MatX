package com.ia61.matx.model.input;

import com.ia61.matx.model.output.Output;

public class InputConnection<TYPE> {

  private Output<TYPE> connectedOutput;

  public InputConnection(Output<TYPE> connectedOutput) {
    this.connectedOutput = connectedOutput;
  }

  public TYPE requestData() {
    return connectedOutput.gatherData();
  }

}
