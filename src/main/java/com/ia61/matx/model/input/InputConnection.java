package com.ia61.matx.model.input;

import com.ia61.matx.model.output.OutputConnection;
import com.ia61.matx.module.Module;

public class InputConnection {

  private Module connectedModule;
  private OutputConnection connectedOutput;

  public InputConnection(Module module, OutputConnection connectedOutput) {
    this.connectedModule = module;
    this.connectedOutput = connectedOutput;
  }

  public Float requestData(Long timestamp) {
    return connectedOutput.gatherData(timestamp);
  }

  public Module getConnectedModule() {
    return connectedModule;
  }
}
