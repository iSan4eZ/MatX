package com.ia61.matx.model.input;

import com.ia61.matx.model.output.Output;

public class AbstractInput<TYPE> implements Input<TYPE> {

  private Output<TYPE> connectedOutput;

  public AbstractInput(Output<TYPE> connectedOutput) {
    this.connectedOutput = connectedOutput;
  }

  public TYPE requestData() {
    return connectedOutput.gatherData();
  }

}
