package com.ia61.matx.model.ui;

import com.ia61.matx.module.Module;
import com.ia61.matx.service.factory.ModuleFactory;
import com.ia61.matx.service.factory.impl.*;

public enum ModuleIcon {

  CORR("КОР", new CorrelatorFactory()),
  INTERRUPTER("ТГ", new TactInterrupterFactory()),
  DECISIONMAKER("МПР", new DecisionMakerFactory()),
  MONITOR("МОН", new SignalMonitorFactory()),
  NOISE("ГЗ", new NoiseSignalGeneratorFactor()),
  SIN("ГГС", new SinSignalGeneratorFactory()),
  DIGITAL("ГДС", new DigitalSignalGeneratorFactory()),
  PSUMM("ПСУМ", new ProportionalSignalSummatorFactory()),
  SSUM("СУМ", new SignalSummatorFactory());

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
