package com.ia61.matx.module.impl.decision_maker;

import com.ia61.matx.module.Module;

public interface DecisionMaker extends Module {

  void calculateSymbolValues(Long timestamp);

}
