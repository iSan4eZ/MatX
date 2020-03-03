package com.ia61.matx.model.input.impl;

import com.ia61.matx.model.input.AbstractInput;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public abstract class MultiInput<TYPE> {

  private List<AbstractInput<TYPE>> inputList = new ArrayList<>();

}
