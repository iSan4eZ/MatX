package com.ia61.matx.module.impl.signal_generator;

import com.ia61.matx.model.output.impl.SingleOutput;
import com.ia61.matx.model.signal.Signal;
import com.ia61.matx.module.Module;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractSignalGeneratorModule implements Module, SingleOutput<Signal> {

  private Long discretizationFrequency;
  private Long lenght;

}
