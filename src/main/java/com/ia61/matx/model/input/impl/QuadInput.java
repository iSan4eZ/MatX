package com.ia61.matx.model.input.impl;

import com.ia61.matx.model.input.Input;
import com.ia61.matx.model.input.InputConnection;
import com.ia61.matx.model.output.OutputConnection;
import com.ia61.matx.model.output.impl.SingleOutput;
import lombok.Getter;

@Getter
public abstract class QuadInput implements Input {

  private InputConnection firstInput;
  private InputConnection secondInput;
  private InputConnection thirdInput;
  private InputConnection fourthInput;

  public void connectFirstInput(SingleOutput singleOutput){
    firstInput = getConnection(singleOutput);
  }

  public void connectSecondInput(SingleOutput singleOutput){
    secondInput = getConnection(singleOutput);
  }

  public void connectThirdInput(SingleOutput singleOutput){
    thirdInput = getConnection(singleOutput);
  }

  public void connectFourthInput(SingleOutput singleOutput) {
    fourthInput = getConnection(singleOutput);
  }

  public int getInputCount() {
    return 4;
  }

  public Boolean connectToInput(OutputConnection output, Integer inputNumber) {
    final InputConnection inputConnection = getInputConnection(output);
    switch (inputNumber) {
      case 0:
        firstInput = inputConnection;
        break;
      case 1:
        secondInput = inputConnection;
        break;
      case 2:
        thirdInput = inputConnection;
        break;
      case 3:
        fourthInput = inputConnection;
        break;
      default:
        return false;
    }
    return true;
  }

  public void disconnectFromInput(Integer inputNumber) {
    switch (inputNumber) {
      case 0:
        firstInput = null;
        return;
      case 1:
        secondInput = null;
        return;
      case 2:
        thirdInput = null;
        return;
      case 3:
        fourthInput = null;
    }
  }
}
