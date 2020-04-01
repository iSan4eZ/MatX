package com.ia61.matx.service.factory.impl;

import com.ia61.matx.module.Module;
import com.ia61.matx.module.impl.summator.impl.ProportionalSignalSummatorModule;
import com.ia61.matx.service.factory.ModuleFactory;

public class ProportionalSignalSummatorFactory implements ModuleFactory {

  @Override
  public Module getNewModule() {
    return new ProportionalSignalSummatorModule();
  }
}

