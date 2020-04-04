package com.ia61.matx.service.factory.impl;

import com.ia61.matx.module.Module;
import com.ia61.matx.module.impl.monitor.impl.SignalMonitor;
import com.ia61.matx.service.factory.ModuleFactory;

public class SignalMonitorFactory implements ModuleFactory {

  @Override
  public Module getNewModule() {
    return new SignalMonitor();
  }
}
