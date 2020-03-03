package com.ia61.matx.service;

import javafx.scene.chart.LineChart;

/**
 * @author Dmytro Vovk
 */

public interface ChartService {
    void buildChart(LineChart<?, ?> lineChart);
}
