package com.ia61.matx.service.factory.impl;

import com.ia61.matx.module.Module;
import com.ia61.matx.module.impl.correlator.impl.CorrelatorModule;
import com.ia61.matx.service.factory.ModuleFactory;

public class CorrelatorFactory implements ModuleFactory {

  @Override
  public Module getNewModule() {
    return new CorrelatorModule();
  }
}
