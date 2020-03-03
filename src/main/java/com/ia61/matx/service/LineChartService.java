package com.ia61.matx.service;

import java.util.List;
import java.util.Map;

/**
 * @author Dmytro Vovk
 */

public interface LineChartService extends ChartService {
    List<Map<Float, Float>> generateSinChart();

    List<Map<Float, Float>> generateNoiseChart();

}
