package com.ia61.matx.service.impl;

import com.ia61.matx.model.input.AbstractInput;
import com.ia61.matx.module.impl.monitor.impl.SignalMonitor;
import com.ia61.matx.module.impl.signal_generator.impl.NoiseSignalGeneratorModule;
import com.ia61.matx.module.impl.signal_generator.impl.SinSignalGeneratorModule;
import com.ia61.matx.module.impl.summator.ProportionalSignalSummatorModule;
import com.ia61.matx.service.LineChartService;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
//        System.out.println("here");
        overlayNoiseOverSinChart().forEach(coordinatesMap -> {
            series.getData().addAll(coordinatesMap.entrySet().stream()
                .map(entry -> new XYChart.Data(entry.getKey().toString(), entry.getValue()))
                .collect(Collectors.toList()));
        });
//        series.getData().add(new XYChart.Data("1", 25));
        lineChart.getData().addAll(series);
    }


    @Override
    public List<Map<Float, Float>> generateSinChart() {
        SinSignalGeneratorModule sinSignalGeneratorModule = new SinSignalGeneratorModule();
        sinSignalGeneratorModule.setLenght(10000L);
        sinSignalGeneratorModule.setDiscretizationFrequency(50L);

        SignalMonitor signalMonitor = new SignalMonitor();
        signalMonitor.setPullRate(50L);
        signalMonitor.setPullTime(10000L);
        signalMonitor.getInputList().add(new AbstractInput<>(sinSignalGeneratorModule.getFirstOutput()));
        return signalMonitor.gatherAllInputs();
    }


    @Override
    public List<Map<Float, Float>> generateNoiseChart() {
        NoiseSignalGeneratorModule noiseSignalGeneratorModule = new NoiseSignalGeneratorModule();
        noiseSignalGeneratorModule.setLenght(10000L);
        noiseSignalGeneratorModule.setDiscretizationFrequency(50L);

        SignalMonitor signalMonitor = new SignalMonitor();
        signalMonitor.setPullRate(50L);
        signalMonitor.setPullTime(10000L);
        signalMonitor.getInputList().add(new AbstractInput<>(noiseSignalGeneratorModule.getFirstOutput()));
        return signalMonitor.gatherAllInputs();
    }

    public List<Map<Float, Float>> overlayNoiseOverSinChart() {
        SinSignalGeneratorModule sinSignalGeneratorModule = new SinSignalGeneratorModule();
        sinSignalGeneratorModule.setLenght(10000L);
        sinSignalGeneratorModule.setDiscretizationFrequency(50L);
        sinSignalGeneratorModule.setInterval(5000.0);

        NoiseSignalGeneratorModule noiseSignalGeneratorModule = new NoiseSignalGeneratorModule();
        noiseSignalGeneratorModule.setLenght(10000L);
        noiseSignalGeneratorModule.setDiscretizationFrequency(50L);

        ProportionalSignalSummatorModule proportionalSignalSummatorModule = new ProportionalSignalSummatorModule();
        proportionalSignalSummatorModule.setFirstSignalCoefficient(1f);
        proportionalSignalSummatorModule.setSecondSignalCoefficient(0.1f);

        proportionalSignalSummatorModule.setFirstInput(new AbstractInput<>(sinSignalGeneratorModule.getFirstOutput()));
        proportionalSignalSummatorModule.setSecondInput(new AbstractInput<>(noiseSignalGeneratorModule.getFirstOutput()));

        SignalMonitor signalMonitor = new SignalMonitor();
        signalMonitor.setPullRate(50L);
        signalMonitor.setPullTime(10000L);
        signalMonitor.getInputList().add(new AbstractInput<>(proportionalSignalSummatorModule.getFirstOutput()));
        return signalMonitor.gatherAllInputs();
    }
}
