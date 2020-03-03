package com.ia61.matx.module.impl.monitor.impl;

import com.ia61.matx.model.input.InputConnection;
import com.ia61.matx.module.impl.monitor.AbstractMonitorModule;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class SignalMonitor extends AbstractMonitorModule {

  @Override
  public void gatherAllInputs(Long timestamp) {
    List<SortedMap<Long, Float>> result = getResult();
    while (result.size() < getInputList().size()) {
      result.add(new TreeMap<>());
    }
    for (int i = 0; i < getInputList().size(); i++) {
      InputConnection signalInputConnection = getInputList().get(i);
      result.get(i).put(timestamp, signalInputConnection.requestData(timestamp));
    }
  }

  @Override
  public String getModuleName() {
    return "Signal Monitor";
  }
}
