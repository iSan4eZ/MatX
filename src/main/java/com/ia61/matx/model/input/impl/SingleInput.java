package com.ia61.matx.model.input.impl;

import com.ia61.matx.model.input.AbstractInput;
import lombok.Data;

@Data
public abstract class SingleInput<TYPE> {

  private AbstractInput<TYPE> firstInput;

}
