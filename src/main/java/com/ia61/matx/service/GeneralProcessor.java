package com.ia61.matx.service;

import com.ia61.matx.model.output.Output;
import com.ia61.matx.module.impl.interrupter.Interrupter;
import com.ia61.matx.module.impl.interrupter.impl.CorrelatorInterrupterModule;
import com.ia61.matx.module.impl.monitor.Monitor;
import com.ia61.matx.module.impl.signal_generator.SignalGenerator;

import java.util.List;

public class GeneralProcessor {

  public static List<Monitor> monitorList;
  public static List<Interrupter> interrupterList;
  public static List<SignalGenerator> signalGeneratorList;

  public static void simulate(){
  }

}
