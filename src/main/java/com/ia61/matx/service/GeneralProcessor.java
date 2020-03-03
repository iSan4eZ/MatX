package com.ia61.matx.service;

import com.ia61.matx.model.output.Output;
import com.ia61.matx.module.impl.interrupter.Interrupter;
import com.ia61.matx.module.impl.interrupter.impl.CorrelatorInterrupterModule;
import com.ia61.matx.module.impl.monitor.Monitor;
import com.ia61.matx.module.impl.signal_generator.SignalGenerator;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GeneralProcessor {

  public static List<Monitor> monitorList = new ArrayList<>();
  public static List<Interrupter> interrupterList = new ArrayList<>();

  private static Long discretizationTime = 5L;
  private static Long simulationTime = 1000L;

  public static void simulate() {
    monitorList.forEach(Monitor::resetResult);
    for (Long i = 0L; i < simulationTime; i += discretizationTime) {
      Long finalI = i;
      interrupterList.forEach(interrupter -> interrupter.interruptAll(finalI));
      monitorList.forEach(monitor -> monitor.gatherAllInputs(finalI));
    }
  }

}
