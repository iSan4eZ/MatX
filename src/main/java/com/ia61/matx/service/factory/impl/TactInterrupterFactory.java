package com.ia61.matx.service.factory.impl;

import com.ia61.matx.module.Module;
import com.ia61.matx.module.impl.interrupter.impl.TactInterrupterModule;
import com.ia61.matx.service.factory.ModuleFactory;

public class TactInterrupterFactory implements ModuleFactory {

  @Override
  public Module getNewModule() {
    return new TactInterrupterModule();
  }
}
