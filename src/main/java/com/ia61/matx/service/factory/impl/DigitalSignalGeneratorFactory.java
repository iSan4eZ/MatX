package com.ia61.matx.service.factory.impl;

import com.ia61.matx.module.Module;
import com.ia61.matx.module.impl.signal_generator.impl.DigitalSignalGeneratorModule;
import com.ia61.matx.service.factory.ModuleFactory;

public class DigitalSignalGeneratorFactory implements ModuleFactory {

  @Override
  public Module getNewModule() {
    return new DigitalSignalGeneratorModule();
  }
}
