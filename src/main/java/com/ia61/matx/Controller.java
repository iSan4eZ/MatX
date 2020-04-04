package com.ia61.matx;

import com.ia61.matx.model.ui.PropertyWindow;
import com.ia61.matx.module.Module;
import com.ia61.matx.module.impl.signal_generator.impl.DigitalSignalGeneratorModule;
import com.ia61.matx.service.LineChartService;
import com.ia61.matx.service.NodeService;
import com.ia61.matx.service.WindowService;
import com.ia61.matx.service.impl.LineChartServiceImpl;
import com.ia61.matx.service.impl.NodeServiceImpl;
import com.ia61.matx.service.impl.WindowServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    AnchorPane elements;

    @FXML
    AnchorPane workingPane;

    @FXML
    Button monitorButton;

    @FXML
    Button new_proprty_btn;

    Module module = new DigitalSignalGeneratorModule();

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
        handlePropertyButton();
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

            FXMLLoader fxmlLoader = new FXMLLoader(
                Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("LineChart.fxml")));
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(fxmlLoader.load(), 500, 500));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.show();
        });
    }

    private void handlePropertyButton() {
        new_proprty_btn.setOnMouseClicked((event) -> {
            new PropertyWindow(module.getPopupFields());
        });
    }
}
