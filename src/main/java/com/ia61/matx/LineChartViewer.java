package com.ia61.matx;

import com.ia61.matx.module.impl.monitor.impl.SignalMonitor;
import com.ia61.matx.service.LineChartService;
import com.ia61.matx.service.impl.LineChartServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Dmytro Vovk
 */

public class LineChartViewer extends BorderPane {

    @FXML BorderPane rootViewerPane;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    private LineChartService lineChartService;

    LineChartViewer() {
        this.lineChartService = new LineChartServiceImpl();

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/LineChart.fxml")
        );

        //fxmlLoader.setRoot(this);
        //fxmlLoader.setController(this);

        try {
            fxmlLoader.load();

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() {
        lineChartService.buildChart(lineChart);
    }

}
