package com.ia61.matx.module.impl.signal_generator.impl;

import com.ia61.matx.module.impl.signal_generator.AbstractSignalGeneratorModule;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SinSignalGeneratorModule extends AbstractSignalGeneratorModule {

  public Double halfInterval = Math.PI;
  public Float height = 1f;

  @Override
  public Float getDataToFirstOutput(Long timestamp) {
    return (float) Math.sin((Math.PI * timestamp) / halfInterval) * height;
  }

  @Override
  public String getModuleName() {
    return "Sinus Signal Generator";
  }
}
