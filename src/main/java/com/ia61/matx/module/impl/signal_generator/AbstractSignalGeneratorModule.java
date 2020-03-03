package com.ia61.matx.module.impl.signal_generator;

import com.ia61.matx.model.output.impl.SingleOutput;
import com.ia61.matx.model.signal.Signal;
import com.ia61.matx.module.Module;
import lombok.*;

@Getter
@Setter
public abstract class AbstractSignalGeneratorModule implements SignalGenerator, SingleOutput {

}
