package com.ia61.matx.module.impl.monitor.impl;

import com.ia61.matx.model.input.AbstractInput;
import com.ia61.matx.model.signal.Signal;
import com.ia61.matx.module.impl.monitor.AbstractMonitorModule;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SignalMonitor extends AbstractMonitorModule<Signal> {

    @Override
    public List<Map<Float, Float>> gatherAllInputs() {
        return getInputList().stream()
                .map(input -> input.requestData().getData(getPullRate(), getPullTime()))
                .collect(Collectors.toList());
    }

    @Override
    public String getModuleName() {
        return "Signal Monitor";
    }
}
