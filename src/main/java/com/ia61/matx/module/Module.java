package com.ia61.matx.module;

import com.ia61.matx.model.input.Input;
import com.ia61.matx.model.output.Output;

public interface Module extends Input, Output {

  String getModuleName();

}
