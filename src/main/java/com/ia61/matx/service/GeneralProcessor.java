package com.ia61.matx.service;

import com.ia61.matx.model.exception.ModuleException;
import com.ia61.matx.module.Module;
import com.ia61.matx.module.impl.decision_maker.DecisionMaker;
import com.ia61.matx.module.impl.interrupter.Interrupter;
import com.ia61.matx.module.impl.monitor.Monitor;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class GeneralProcessor {

  public static List<Monitor> monitorList = new ArrayList<>();
  public static List<Interrupter> interrupterList = new ArrayList<>();
  public static List<DecisionMaker> decisionMakerList = new ArrayList<>();

  private static Long simulationTime = 4000L;
  private static Long currentTime = 0L;

  private static Service<String> progress = new Service<String>() {
    @Override
    protected Task<String> createTask() {
      return new Task<String>() {
        @Override
        protected String call() throws Exception {
          try {
            monitorList.forEach(Monitor::resetResult);
            decisionMakerList.forEach(DecisionMaker::resetResult);
            for (long i = 0L; i < simulationTime; i += 1) {
              long finalI = i;
              updateProgress(i, simulationTime);
              interrupterList.forEach(interrupter -> interrupter.interruptAll(finalI));
              monitorList.forEach(monitor -> monitor.gatherAllInputs(finalI));
              decisionMakerList.forEach(decisionMaker -> decisionMaker.calculateSymbolValues(finalI));
            }
            return "Симуляція пройшла успішно!";
          } catch (ModuleException e) {
            return e.getModule().getModuleName() + " має порожні входи.";
          } catch (Exception e) {
            log.error("Error in simulation", e);
            return "Виникла помилка.";
          }
        }
      };
    }
  };

  public static Long getSimulationTime() {
    return simulationTime;
  }

  public static void setSimulationTime(Long simulationTime) {
    GeneralProcessor.simulationTime = simulationTime;
  }

  public static Service<String> getProgress() {
    return progress;
  }

  public static void removeModule(Module moduleToRemove) {
    monitorList.remove(moduleToRemove);
    interrupterList.remove(moduleToRemove);
    decisionMakerList.remove(moduleToRemove);
  }
}
