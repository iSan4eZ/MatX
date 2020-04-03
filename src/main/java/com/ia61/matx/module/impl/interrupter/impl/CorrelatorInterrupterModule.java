package com.ia61.matx.module.impl.interrupter.impl;

import com.ia61.matx.module.impl.correlator.impl.CorrelatorModule;
import com.ia61.matx.module.impl.interrupter.AbstractInterrupter;

public class CorrelatorInterrupterModule extends AbstractInterrupter<CorrelatorModule> {

  @Override
  public String getModuleName() {
    return "Тактовый Генератор";
  }

}
