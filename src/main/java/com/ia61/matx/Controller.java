package com.ia61.matx;

import com.ia61.matx.service.LineChartService;
import com.ia61.matx.service.NodeService;
import com.ia61.matx.service.WindowService;
import com.ia61.matx.service.impl.LineChartServiceImpl;
import com.ia61.matx.service.impl.NodeServiceImpl;
import com.ia61.matx.service.impl.WindowServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    AnchorPane elements;

    @FXML
    AnchorPane workingPane;

    @FXML
    Button monitorButton;

    private NodeService nodeService;
    private LineChartService lineChartService;
    private WindowService windowService;

    public Controller() {
        this.windowService = new WindowServiceImpl();
        this.lineChartService = new LineChartServiceImpl();
        this.nodeService = new NodeServiceImpl();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handleMonitorButton();
        //elementsTree.setRoot((TreeItem<String>) nodeService.getNativeNodes());
        final StackPane sp1 = new StackPane();
        //Circle circle = new Circle(150.0f, 150.0f, 80.f);
//        sp1.getChildren().add(nodeService.getNativeNodes());
        //workingPane.getChildren().add(nodeService.getNativeNodes());
//        elementsPane.(nodeService.getNativeNodes());

        elements.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent
                -> workingPane.getChildren().add(nodeService.getNativeNodes()));


    }

    private void handleMonitorButton() {
        monitorButton.setOnMouseClicked((event) -> {
//            windowService.buildWindow("LineChart.fxml");
            LineChartViewer lineChartViewer = new LineChartViewer();
        });
    }
}
