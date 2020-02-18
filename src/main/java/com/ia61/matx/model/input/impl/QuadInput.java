package com.ia61.matx.model.input.impl;

import com.ia61.matx.model.input.AbstractInput;
import lombok.Data;

@Data
public abstract class QuadInput<TYPE> {

  private AbstractInput<TYPE> firstInput;
  private AbstractInput<TYPE> secondInput;
  private AbstractInput<TYPE> thirdInput;
  private AbstractInput<TYPE> fourthInput;

}
