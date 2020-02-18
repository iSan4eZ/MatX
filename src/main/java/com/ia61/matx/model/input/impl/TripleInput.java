package com.ia61.matx.model.input.impl;

import com.ia61.matx.model.input.AbstractInput;
import lombok.Data;

@Data
public abstract class TripleInput<TYPE> {

  private AbstractInput<TYPE> firstInput;
  private AbstractInput<TYPE> secondInput;
  private AbstractInput<TYPE> thirdInput;

}
