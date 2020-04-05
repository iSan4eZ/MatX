package com.ia61.matx.service;

import com.ia61.matx.module.impl.decision_maker.DecisionMaker;
import com.ia61.matx.module.impl.interrupter.Interrupter;
import com.ia61.matx.module.impl.monitor.Monitor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GeneralProcessor {

  public static List<Monitor> monitorList = new ArrayList<>();
  public static List<Interrupter> interrupterList = new ArrayList<>();
  public static List<DecisionMaker> decisionMakerList = new ArrayList<>();

  private static Long simulationTime = 4000L;

  public static void simulate() {
    monitorList.forEach(Monitor::resetResult);
    for (long i = 0L; i < simulationTime; i += 1) {
      long finalI = i;
      interrupterList.forEach(interrupter -> interrupter.interruptAll(finalI));
      monitorList.forEach(monitor -> monitor.gatherAllInputs(finalI));
      decisionMakerList.forEach(decisionMaker -> decisionMaker.calculateSymbol(finalI));
    }
  }

}
