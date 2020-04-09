package com.ia61.matx.model.output;

import java.io.Serializable;
import java.util.Optional;

public interface Output extends Serializable {

  int getOutputCount();

  Optional<OutputConnection> getOutput(Integer outputNumber);

}
