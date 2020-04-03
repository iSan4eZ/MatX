package com.ia61.matx.service.impl;

import com.ia61.matx.module.impl.correlator.impl.CorrelatorModule;
import com.ia61.matx.module.impl.interrupter.impl.CorrelatorInterrupterModule;
import com.ia61.matx.module.impl.monitor.AbstractMonitorModule;
import com.ia61.matx.module.impl.monitor.impl.SignalMonitor;
import com.ia61.matx.module.impl.signal_generator.impl.NoiseSignalGeneratorModule;
import com.ia61.matx.module.impl.signal_generator.impl.SinSignalGeneratorModule;
import com.ia61.matx.module.impl.summator.impl.ProportionalSignalSummatorModule;
import com.ia61.matx.service.GeneralProcessor;
import com.ia61.matx.service.LineChartService;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.List;
import java.util.SortedMap;
import java.util.stream.Collectors;

/**
 * @author Dmytro Vovk
 */

public class LineChartServiceImpl implements LineChartService {

    @Override
    public void buildChart(LineChart<?, ?> lineChart) {

        GeneralProcessor.simulate();

        if (GeneralProcessor.monitorList.size() != 0)
            ((AbstractMonitorModule) GeneralProcessor.monitorList.get(0)).getResult()

//        overlayNoiseOverSinChart()
//        correlateSinChart()
            .forEach(coordinatesMap -> {
            XYChart.Series series = new XYChart.Series();
            series.getData().addAll(coordinatesMap.entrySet().stream()
                .map(entry -> new XYChart.Data(entry.getKey().toString(), entry.getValue()))
                .collect(Collectors.toList()));
            lineChart.getData().add(series);
        });

    }

    public List<SortedMap<Long, Float>> overlayNoiseOverSinChart() {
        //Sin generator
        SinSignalGeneratorModule sinSignalGeneratorModule = new SinSignalGeneratorModule();
        sinSignalGeneratorModule.setFrequency(2f);

        //Noise generator
        NoiseSignalGeneratorModule noiseSignalGeneratorModule = new NoiseSignalGeneratorModule();

        //Proportional summator
        ProportionalSignalSummatorModule proportionalSignalSummatorModule = new ProportionalSignalSummatorModule();
        proportionalSignalSummatorModule.setFirstSignalCoefficient(1f);
        proportionalSignalSummatorModule.setSecondSignalCoefficient(0f);

        proportionalSignalSummatorModule.connectFirstInput(sinSignalGeneratorModule);
        proportionalSignalSummatorModule.connectSecondInput(noiseSignalGeneratorModule);

        //Monitor
        SignalMonitor signalMonitor = new SignalMonitor();
        signalMonitor.setPullRate(50L);
        signalMonitor.connectInput(proportionalSignalSummatorModule);

        GeneralProcessor.simulate();

        return signalMonitor.getResult();
    }

    public List<SortedMap<Long, Float>> correlateSinChart() {
        //Sin generator
        SinSignalGeneratorModule sinSignalGeneratorModule = new SinSignalGeneratorModule();
        sinSignalGeneratorModule.setFrequency(3f);
        sinSignalGeneratorModule.setPeriodsPerSymbol(1);
        sinSignalGeneratorModule.setSymbol("101");

        //Perfect sin generator
        SinSignalGeneratorModule perfectSignalGeneratorModule = new SinSignalGeneratorModule();
        perfectSignalGeneratorModule.setFrequency(3f);
        perfectSignalGeneratorModule.setPeriodsPerSymbol(1);
        perfectSignalGeneratorModule.setSymbol("1");
        perfectSignalGeneratorModule.setRepeatable(true);

        //Correlator
        CorrelatorModule correlatorModule = new CorrelatorModule();
        correlatorModule.connectFirstInput(sinSignalGeneratorModule);
        correlatorModule.connectSecondInput(perfectSignalGeneratorModule);

        //Interrupter
        CorrelatorInterrupterModule correlatorInterrupterModule = new CorrelatorInterrupterModule();
        correlatorInterrupterModule.setInterruptFrequency(500L);
        correlatorInterrupterModule.addInterruptable(correlatorModule);

        //Monitor
        SignalMonitor signalMonitor = new SignalMonitor();
        signalMonitor.setPullRate(50L);
        signalMonitor.connectInput(correlatorModule);
//        signalMonitor.connectInput(sinSignalGeneratorModule);
//        signalMonitor.connectInput(perfectSignalGeneratorModule);

        GeneralProcessor.simulate();

        return signalMonitor.getResult();
    }
}
