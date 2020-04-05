package com.ia61.matx.module.impl.interrupter.impl;

import com.ia61.matx.module.impl.interrupter.AbstractInterrupter;

public class TactInterrupterModule extends AbstractInterrupter {

  @Override
  public String getModuleName() {
    return "Тактовий генератор";
  }

  @Override
  public Float getDataToFirstOutput(Long timestamp) {
    return 0f;
  }
}
