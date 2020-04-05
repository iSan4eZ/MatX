package com.ia61.matx.service.impl;

import com.ia61.matx.module.impl.correlator.impl.CorrelatorModule;
import com.ia61.matx.module.impl.decision_maker.impl.DecisionMakerModule;
import com.ia61.matx.module.impl.interrupter.impl.TactInterrupterModule;
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
//
//    if (GeneralProcessor.monitorList.size() != 0) {
//      ((AbstractMonitorModule) GeneralProcessor.monitorList.get(0)).getResult()

//        overlayNoiseOverSinChart()
    correlateSinChart()
        .forEach(coordinatesMap -> {
          XYChart.Series series = new XYChart.Series();
          series.getData().addAll(coordinatesMap.entrySet().stream()
              .map(entry -> new XYChart.Data(entry.getKey().toString(), entry.getValue()))
              .collect(Collectors.toList()));
          lineChart.getData().add(series);
        });
//    }

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

    proportionalSignalSummatorModule.connectToInput(sinSignalGeneratorModule, 0, 0);
    proportionalSignalSummatorModule.connectToInput(noiseSignalGeneratorModule, 0, 1);

    //Monitor
    SignalMonitor signalMonitor = new SignalMonitor();
    signalMonitor.setPullRate(50L);
    signalMonitor.connectToInput(proportionalSignalSummatorModule, 0, 0);

    GeneralProcessor.simulate();

    return signalMonitor.getResult();
  }

  public List<SortedMap<Long, Float>> correlateSinChart() {
    //Sin generator
    SinSignalGeneratorModule sinSignalGeneratorModule = new SinSignalGeneratorModule();
    sinSignalGeneratorModule.setFrequency(3f);
    sinSignalGeneratorModule.setPeriodsPerSymbol(1);
    sinSignalGeneratorModule.setSymbol("11101");

    //Perfect sin generator
    SinSignalGeneratorModule perfectSignalGeneratorModule = new SinSignalGeneratorModule();
    perfectSignalGeneratorModule.setFrequency(3f);
    perfectSignalGeneratorModule.setPeriodsPerSymbol(1);
    perfectSignalGeneratorModule.setSymbol("1");
    perfectSignalGeneratorModule.setRepeatable(true);

    //Interrupter
    TactInterrupterModule tactInterrupterModule = new TactInterrupterModule();
    tactInterrupterModule.setInterruptFrequency(3L);

    //Correlator
    CorrelatorModule correlatorModule = new CorrelatorModule();
    correlatorModule.connectToInput(sinSignalGeneratorModule, 0, 0);
    correlatorModule.connectToInput(perfectSignalGeneratorModule, 0, 1);
    correlatorModule.connectToInput(tactInterrupterModule, 0, 2);

    DecisionMakerModule decisionMakerModule = new DecisionMakerModule();
    decisionMakerModule.connectToInput(correlatorModule, 0, 0);
    decisionMakerModule.connectToInput(tactInterrupterModule, 0, 1);

    //Monitor
    SignalMonitor signalMonitor = new SignalMonitor();
    signalMonitor.setPullRate(10L);
    signalMonitor.connectToInput(correlatorModule, 0, 0);
//        signalMonitor.connectInput(sinSignalGeneratorModule);
//        signalMonitor.connectInput(perfectSignalGeneratorModule);

    GeneralProcessor.simulate();

    return signalMonitor.getResult();
  }
}
