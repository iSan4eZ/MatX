package com.ia61.matx.service.factory.impl;

import com.ia61.matx.module.Module;
import com.ia61.matx.module.impl.decision_maker.impl.DecisionMakerModule;
import com.ia61.matx.service.factory.ModuleFactory;

public class DecisionMakerFactory implements ModuleFactory {

  @Override
  public Module getNewModule() {
    return new DecisionMakerModule();
  }
}
