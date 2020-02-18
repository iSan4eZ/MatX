package com.ia61.matx.module.impl.signal_generator.impl;

import com.ia61.matx.model.signal.Signal;
import com.ia61.matx.model.signal.impl.AnalogAbstractSignal;
import com.ia61.matx.module.impl.signal_generator.AbstractSignalGeneratorModule;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NoizeSignalGeneratorModule extends AbstractSignalGeneratorModule {

  public Float height = 1f;

  @Override
  public Signal getDataToFirstOutput() {
    final List<Float> data = new ArrayList<>();
    for (long i = 0; i <= getLenght(); i += getDiscretizationFrequency()) {
      data.add((float) Math.random() * height);
    }
    return new AnalogAbstractSignal(data, getDiscretizationFrequency());
  }
}
