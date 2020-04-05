package com.ia61.matx.model.ui;

import com.ia61.matx.module.Module;
import com.ia61.matx.service.factory.ModuleFactory;
import com.ia61.matx.service.factory.impl.*;

public enum ModuleIcon {

  CORR("Correlator", new CorrelatorFactory()),
  INTERRAPTOR("TactInterrupter", new TactInterrupterFactory()),
  MONITOR("Monitor", new SignalMonitorFactory()),
  NOISE("NoiseSignal", new NoiseSignalGeneratorFactor()),
  SIN("SinSignal", new SinSignalGeneratorFactory()),
  PSUMM("PropSummator", new ProportionalSignalSummatorFactory()),
  SSUM("SignalSummator", new SignalSummatorFactory());

  private String name;
  private ModuleFactory factory;

  ModuleIcon(String name, ModuleFactory factory) {
    this.name = name;
    this.factory = factory;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Module getModule() {
    return factory.getNewModule();
  }
}
