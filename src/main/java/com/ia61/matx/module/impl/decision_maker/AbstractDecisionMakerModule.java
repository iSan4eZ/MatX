package com.ia61.matx.module.impl.decision_maker;

import com.ia61.matx.model.input.impl.DualInput;
import com.ia61.matx.model.output.impl.NoOutput;
import com.ia61.matx.module.impl.interrupter.Interruptable;
import com.ia61.matx.service.GeneralProcessor;

public abstract class AbstractDecisionMakerModule extends DualInput implements DecisionMaker, NoOutput, Interruptable {

  public AbstractDecisionMakerModule() {
    GeneralProcessor.decisionMakerList.add(this);
  }

}
