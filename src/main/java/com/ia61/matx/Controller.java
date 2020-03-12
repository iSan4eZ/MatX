package com.ia61.matx;

import com.ia61.matx.model.ui.*;
import com.ia61.matx.service.LineChartService;
import com.ia61.matx.service.NodeService;
import com.ia61.matx.service.WindowService;
import com.ia61.matx.service.impl.LineChartServiceImpl;
import com.ia61.matx.service.impl.NodeServiceImpl;
import com.ia61.matx.service.impl.WindowServiceImpl;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller extends BorderPane implements Initializable {

    @FXML
    BorderPane rootPane;

    @FXML
    AnchorPane elements;

    @FXML
    AnchorPane workingPane;

    @FXML
    Button monitorButton;

    private NodeService nodeService;
    private LineChartService lineChartService;
    private WindowService windowService;

    private EventHandler<DragEvent> mIconDragOverRoot = null;
    private EventHandler<DragEvent> mIconDragDropped = null;
    private EventHandler<DragEvent> mIconDragOverRightPane = null;
    private DragIcon mDragOverIcon = null;

    public Controller() {
        this.windowService = new WindowServiceImpl();
        this.lineChartService = new LineChartServiceImpl();
        this.nodeService = new NodeServiceImpl();
    }

    @Override //TODO @FXML
    public void initialize(URL location, ResourceBundle resources) {
        handleMonitorButton();
        final StackPane sp1 = new StackPane();

        mDragOverIcon = new DragIcon();

        mDragOverIcon.setVisible(false);
        mDragOverIcon.setOpacity(0.65);
        getChildren().add(mDragOverIcon);

        Node node = nodeService.getNativeNodes();
        DragIcon icn = new DragIcon();

        addDragDetection((DraggableNode) node);

        elements.getChildren().add(node);

        workingPane.getChildren().add(nodeService.getNativeNodes());

        elements.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            workingPane.getChildren().add(nodeService.getNativeNodes());
        });
    }

    private void addDragDetection(DraggableNode dragIcon) {

        dragIcon.setOnDragDetected (new EventHandler <MouseEvent> () {

            @Override
            public void handle(MouseEvent event) {

                // set drag event handlers on their respective objects
                rootPane.setOnDragOver(mIconDragOverRoot);
                elements.setOnDragOver(mIconDragOverRightPane);
                elements.setOnDragDropped(mIconDragDropped);

                // get a reference to the clicked DragIcon object
                DragIcon icn = (DragIcon) event.getSource();

                //begin drag ops
//                mDragOverIcon.setType(icn.getType());
                mDragOverIcon.relocateToPoint(new Point2D (event.getSceneX(), event.getSceneY()));

                ClipboardContent content = new ClipboardContent();
                DragContainer container = new DragContainer();

                container.addData ("type", mDragOverIcon.getType().toString());
                content.put(DragContainer.DragNode, container);

                //mDragOverIcon.startDragAndDrop (TransferMode.ANY).setContent(content);
                mDragOverIcon.setVisible(true);
                mDragOverIcon.setMouseTransparent(true);
                event.consume();
            }
        });
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
        buildDragHandlers();
    }

    private void buildDragHandlers() {

        //drag over transition to move widget form left pane to right pane
        mIconDragOverRoot = event -> {

            Point2D p = workingPane.sceneToLocal(event.getSceneX(), event.getSceneY());

            //turn on transfer mode and track in the right-pane's context
            //if (and only if) the mouse cursor falls within the right pane's bounds.
            if (!elements.boundsInLocalProperty().get().contains(p)) {

                event.acceptTransferModes(TransferMode.ANY);
                mDragOverIcon.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                return;
            }

            event.consume();
        };

        mIconDragOverRightPane = event -> {

            event.acceptTransferModes(TransferMode.ANY);

            //convert the mouse coordinates to scene coordinates,
            //then convert back to coordinates that are relative to
            //the parent of mDragIcon.  Since mDragIcon is a child of the root
            //pane, coodinates must be in the root pane's coordinate system to work
            //properly.
            mDragOverIcon.relocateToPoint(
                    new Point2D(event.getSceneX(), event.getSceneY())
            );
            event.consume();
        };

        mIconDragDropped = event -> {

            DragContainer container =
                    (DragContainer) event.getDragboard().getContent(DragContainer.AddNode);

            container.addData("scene_coords",
                    new Point2D(event.getSceneX(), event.getSceneY()));

            ClipboardContent content = new ClipboardContent();
            content.put(DragContainer.AddNode, container);

            event.getDragboard().setContent(content);
            event.setDropCompleted(true);
        };

        this.setOnDragDone (event -> {

            workingPane.removeEventHandler(DragEvent.DRAG_OVER, mIconDragOverRightPane);
            workingPane.removeEventHandler(DragEvent.DRAG_DROPPED, mIconDragDropped);
            rootPane.removeEventHandler(DragEvent.DRAG_OVER, mIconDragOverRoot);

            mDragOverIcon.setVisible(false);

            //Create node drag operation
            DragContainer container =
                    (DragContainer) event.getDragboard().getContent(DragContainer.AddNode);

            if (container != null) {
                if (container.getValue("scene_coords") != null) {

                    if (container.getValue("type").equals(DragIconType.cubic_curve.toString())) {
                        CubicCurveDemo curve = new CubicCurveDemo();

                        workingPane.getChildren().add(curve);

                        Point2D cursorPoint = container.getValue("scene_coords");

                        curve.relocateToPoint(
                                new Point2D(cursorPoint.getX() - 32, cursorPoint.getY() - 32)
                        );
                    }
                    else {

                        DraggableNode node = new DraggableNode();

                        node.setType(DragIconType.valueOf(container.getValue("type")));
                        workingPane.getChildren().add(node);

                        Point2D cursorPoint = container.getValue("scene_coords");

                        node.relocateToPoint(
                                new Point2D(cursorPoint.getX() - 32, cursorPoint.getY() - 32)
                        );
                    }
                }
            }
            /*
            //Move node drag operation
            container =
                    (DragContainer) event.getDragboard().getContent(DragContainer.DragNode);

            if (container != null) {
                if (container.getValue("type") != null)
                    System.out.println ("Moved node " + container.getValue("type"));
            }
            */

            //AddLink drag operation
            container =
                    (DragContainer) event.getDragboard().getContent(DragContainer.AddLink);

            if (container != null) {

                //bind the ends of our link to the nodes whose id's are stored in the drag container
                String sourceId = container.getValue("source");
                String targetId = container.getValue("target");

                if (sourceId != null && targetId != null) {

                    //	System.out.println(container.getData());
                    NodeLink link = new NodeLink();

                    //add our link at the top of the rendering order so it's rendered first
                    workingPane.getChildren().add(0,link);

                    DraggableNode source = null;
                    DraggableNode target = null;

                    for (Node n: workingPane.getChildren()) {

                        if (n.getId() == null)
                            continue;

                        if (n.getId().equals(sourceId))
                            source = (DraggableNode) n;

                        if (n.getId().equals(targetId))
                            target = (DraggableNode) n;

                    }

                    if (source != null && target != null)
                        link.bindEnds(source, target);
                }

            }

            event.consume();
        });
    }
}
