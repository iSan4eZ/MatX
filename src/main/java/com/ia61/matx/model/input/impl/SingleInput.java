package com.ia61.matx.model.input.impl;

import com.ia61.matx.model.input.Input;
import com.ia61.matx.model.input.InputConnection;
import com.ia61.matx.model.output.impl.SingleOutput;
import lombok.Getter;

@Getter
public abstract class SingleInput implements Input {

  private InputConnection firstInput;

  public void connectFirstInput(SingleOutput singleOutput){
    firstInput = getConnection(singleOutput);
  }

  @Override
  public int getInputCount() {
    return 1;
  }

}
