package com.ia61.matx.model.output;

import java.io.Serializable;

public interface OutputConnection extends Serializable {

  Float gatherData(Long timestamp);

}
