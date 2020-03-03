package com.ia61.matx.module.impl.interrupter.impl;

import com.ia61.matx.module.impl.correlator.impl.CorrelatorModule;
import com.ia61.matx.module.impl.interrupter.AbstractInterrupter;
import lombok.Data;

import java.util.List;

public class CorrelatorInterrupterModule extends AbstractInterrupter<CorrelatorModule> {

  @Override
  public String getModuleName() {
    return "Correlator";
  }

}
