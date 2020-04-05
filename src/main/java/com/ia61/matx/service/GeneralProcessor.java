package com.ia61.matx.service;

import com.ia61.matx.module.impl.decision_maker.DecisionMaker;
import com.ia61.matx.module.impl.interrupter.Interrupter;
import com.ia61.matx.module.impl.monitor.Monitor;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GeneralProcessor {

  public static List<Monitor> monitorList = new ArrayList<>();
  public static List<Interrupter> interrupterList = new ArrayList<>();
  public static List<DecisionMaker> decisionMakerList = new ArrayList<>();

  private static Long simulationTime = 4000L;
  private static Long currentTime = 0L;

  private static Service progress = new Service() {
    @Override
    protected Task createTask() {
      return new Task() {
        @Override
        protected Object call() throws Exception {
          simulate();
          updateProgress(currentTime, simulationTime);
          return null;
        }
      };
    }
  };

  public static void simulate() {
    monitorList.forEach(Monitor::resetResult);
    for (long i = 0L; i < simulationTime; i += 1) {
      long finalI = i;
      currentTime = i;
      interrupterList.forEach(interrupter -> interrupter.interruptAll(finalI));
      monitorList.forEach(monitor -> monitor.gatherAllInputs(finalI));
      decisionMakerList.forEach(decisionMaker -> decisionMaker.calculateSymbolValues(finalI));
    }
  }

  public static Long getSimulationTime() {
    return simulationTime;
  }

  public static void setSimulationTime(Long simulationTime) {
    GeneralProcessor.simulationTime = simulationTime;
  }

  public static Service getProgress() {
    return progress;
  }
}
