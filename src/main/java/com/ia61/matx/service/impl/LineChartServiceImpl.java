package com.ia61.matx.service.impl;

import com.ia61.matx.model.input.AbstractInput;
import com.ia61.matx.module.impl.monitor.impl.SignalMonitor;
import com.ia61.matx.module.impl.signal_generator.impl.SinSignalGeneratorModule;
import com.ia61.matx.service.LineChartService;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.List;
import java.util.Map;

/**
 * @author Dmytro Vovk
 */

public class LineChartServiceImpl implements LineChartService {

    @Override
    public void buildChart(LineChart<?, ?> lineChart) {
        XYChart.Series series = new XYChart.Series();

//        System.out.println(generateRandomChart());
//        generateRandomChart();
//        series.getData().add(generateRandomChart());
        System.out.println("here");
        series.getData().add(new XYChart.Data("1", 25));
        lineChart.getData().addAll(series);
    }


    @Override
    public List<Map<Float, Float>> generateRandomChart() {
        SinSignalGeneratorModule sinSignalGeneratorModule = new SinSignalGeneratorModule();
        SignalMonitor signalMonitor = new SignalMonitor();
        signalMonitor.getInputList().add(new AbstractInput<>(sinSignalGeneratorModule.getFirstOutput()));
        return signalMonitor.gatherAllInputs();
    }
}
