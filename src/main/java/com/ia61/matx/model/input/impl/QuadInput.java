package com.ia61.matx.model.input.impl;

import com.ia61.matx.model.input.InputConnection;
import com.ia61.matx.model.input.Input;
import com.ia61.matx.model.output.impl.SingleOutput;
import lombok.Data;
import lombok.Getter;

@Getter
public abstract class QuadInput<TYPE> implements Input<TYPE> {

  private InputConnection<TYPE> firstInput;
  private InputConnection<TYPE> secondInput;
  private InputConnection<TYPE> thirdInput;
  private InputConnection<TYPE> fourthInput;

  public void connectFirstInput(SingleOutput<TYPE> singleOutput){
    firstInput = getConnection(singleOutput);
  }

  public void connectSecondInput(SingleOutput<TYPE> singleOutput){
    secondInput = getConnection(singleOutput);
  }

  public void connectThirdInput(SingleOutput<TYPE> singleOutput){
    thirdInput = getConnection(singleOutput);
  }

  public void connectFourthInput(SingleOutput<TYPE> singleOutput){
    fourthInput = getConnection(singleOutput);
  }

  @Override
  public int getInputCount() {
    return 4;
  }
}
