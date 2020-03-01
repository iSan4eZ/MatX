package com.ia61.matx;

import com.ia61.matx.service.NodeService;
import com.ia61.matx.service.impl.NodeServiceImpl;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    AnchorPane elements;

    @FXML
    AnchorPane workingPane;

    private NodeService nodeService;

    public Controller() {
        this.nodeService = new NodeServiceImpl();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //elementsTree.setRoot((TreeItem<String>) nodeService.getNativeNodes());
        final StackPane sp1 = new StackPane();
        //Circle circle = new Circle(150.0f, 150.0f, 80.f);
//        sp1.getChildren().add(nodeService.getNativeNodes());
        //workingPane.getChildren().add(nodeService.getNativeNodes());
//        elementsPane.(nodeService.getNativeNodes());

        elements.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent
                -> workingPane.getChildren().add(nodeService.getNativeNodes()));


    }
}
