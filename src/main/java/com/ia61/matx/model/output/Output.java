package com.ia61.matx.model.output;

import java.util.Optional;

public interface Output {

  int getOutputCount();

  Optional<OutputConnection> getOutput(Integer outputNumber);

}
