package com.ia61.matx.model.input.impl;

import com.ia61.matx.model.input.InputConnection;
import com.ia61.matx.model.input.Input;
import com.ia61.matx.model.output.Output;
import com.ia61.matx.model.output.impl.SingleOutput;
import com.sun.tools.example.debug.tty.TTY;
import lombok.Data;
import lombok.Getter;

import java.util.Objects;

@Getter
public abstract class SingleInput<TYPE> implements Input<TYPE> {

  private InputConnection<TYPE> firstInput;

  public void connectFirstInput(SingleOutput<TYPE> singleOutput){
    firstInput = getConnection(singleOutput);
  }

  @Override
  public int getInputCount() {
    return 1;
  }

}
