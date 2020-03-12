package com.ia61.matx.model.input.impl;

import com.ia61.matx.model.input.InputConnection;

import java.util.ArrayList;
import java.util.List;

import com.ia61.matx.model.input.Input;
import com.ia61.matx.model.output.impl.SingleOutput;
import lombok.Getter;

@Getter
public abstract class MultiInput implements Input {

  private List<InputConnection> inputList = new ArrayList<>();

  public void connectInput(SingleOutput singleOutput) {
    inputList.add(getConnection(singleOutput));
  }

  @Override
  public int getInputCount() {
    return -1;
  }
}
