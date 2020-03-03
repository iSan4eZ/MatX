package com.ia61.matx.model.input.impl;

import com.ia61.matx.model.input.Input;
import lombok.Getter;

@Getter
public abstract class NoInput implements Input {

  @Override
  public int getInputCount() {
    return 0;
  }

}
