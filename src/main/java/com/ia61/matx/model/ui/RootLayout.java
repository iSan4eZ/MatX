package com.ia61.matx.model.ui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

public class RootLayout extends AnchorPane {

  @FXML
  SplitPane base_pane;
  @FXML
  AnchorPane right_pane;
  @FXML
  VBox left_pane;

  @FXML
  Button monitorButton;

  private DragIcon mDragOverIcon = null;

  private EventHandler<DragEvent> mIconDragOverRoot = null;
  private EventHandler<DragEvent> mIconDragDropped = null;
  private EventHandler<DragEvent> mIconDragOverRightPane = null;

  public RootLayout() {

    FXMLLoader fxmlLoader = new FXMLLoader(
        getClass().getResource("/RootLayout.fxml")
    );

    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();

    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  @FXML
  private void initialize() {

    //Add one icon that will be used for the drag-drop process
    //This is added as a child to the root anchorpane so it can be visible
    //on both sides of the split pane.
    mDragOverIcon = new DragIcon();

    mDragOverIcon.setVisible(false);
    mDragOverIcon.setOpacity(0.65);
    getChildren().add(mDragOverIcon);

    //populate left pane with multiple colored icons for testing
    for (int i = 0; i < ModuleIcon.values().length; i++) {

      DragIcon icn = new DragIcon();

      addDragDetection(icn);

//			icn.setType(DragIconType.common);
      icn.setModuleIcon(ModuleIcon.values()[i]);
      left_pane.getChildren().add(icn);
    }

    buildDragHandlers();
    handleMonitorButton();
  }

  private void addDragDetection(DragIcon dragIcon) {

    dragIcon.setOnDragDetected(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {

        // set drag event handlers on their respective objects
        base_pane.setOnDragOver(mIconDragOverRoot);
        right_pane.setOnDragOver(mIconDragOverRightPane);
        right_pane.setOnDragDropped(mIconDragDropped);

        // get a reference to the clicked DragIcon object
        DragIcon icn = (DragIcon) event.getSource();

        //begin drag ops
//				mDragOverIcon.setType(icn.getType());
        mDragOverIcon.setModuleIcon(icn.getModuleIcon());
        mDragOverIcon.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));

        ClipboardContent content = new ClipboardContent();
        DragContainer container = new DragContainer();

        container.addData("type", mDragOverIcon.getModuleIcon());
        content.put(DragContainer.AddNode, container);

        mDragOverIcon.startDragAndDrop(TransferMode.ANY).setContent(content);
        mDragOverIcon.setVisible(true);
        mDragOverIcon.setMouseTransparent(true);
        event.consume();
      }
    });
  }

  private void addLinkDeleteHandler(NodeLink nodeLink) {

    nodeLink.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
          NodeLink nl =
              (NodeLink) event.getSource();

          for (Node n : right_pane.getChildren()) {

            if (n.getId().equals(nl.getSourceId()) || n.getId().equals(nl.getTargetId())) {

              if (n.getId().equals(nl.getTargetId())) {
                DraggableNode targetNode = (DraggableNode) n;
                int number = targetNode.getLinkIds().indexOf(nl.getId());
                targetNode.getModule().disconnectFromInput(number);
              }
              ((DraggableNode) n).removeLink(nl.getId());
            }

          }

          right_pane.getChildren().remove(nl);
        }
      }
    });
  }

  private void buildDragHandlers() {

    //drag over transition to move widget form left pane to right pane
    mIconDragOverRoot = new EventHandler<DragEvent>() {

      @Override
      public void handle(DragEvent event) {

        Point2D p = right_pane.sceneToLocal(event.getSceneX(), event.getSceneY());

        //turn on transfer mode and track in the right-pane's context
        //if (and only if) the mouse cursor falls within the right pane's bounds.
        if (!right_pane.boundsInLocalProperty().get().contains(p)) {

          event.acceptTransferModes(TransferMode.ANY);
          mDragOverIcon.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
          return;
        }

        event.consume();
      }
    };

    mIconDragOverRightPane = new EventHandler<DragEvent>() {

      @Override
      public void handle(DragEvent event) {

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
      }
    };

    mIconDragDropped = new EventHandler<DragEvent>() {

      @Override
      public void handle(DragEvent event) {

        DragContainer container =
            (DragContainer) event.getDragboard().getContent(DragContainer.AddNode);

        container.addData("scene_coords",
            new Point2D(event.getSceneX(), event.getSceneY()));

        ClipboardContent content = new ClipboardContent();
        content.put(DragContainer.AddNode, container);

        event.getDragboard().setContent(content);
        event.setDropCompleted(true);
      }
    };

    this.setOnDragDone(new EventHandler<DragEvent>() {

      @Override
      public void handle(DragEvent event) {

        right_pane.removeEventHandler(DragEvent.DRAG_OVER, mIconDragOverRightPane);
        right_pane.removeEventHandler(DragEvent.DRAG_DROPPED, mIconDragDropped);
        base_pane.removeEventHandler(DragEvent.DRAG_OVER, mIconDragOverRoot);

        mDragOverIcon.setVisible(false);

        //Create node drag operation
        DragContainer container =
            (DragContainer) event.getDragboard().getContent(DragContainer.AddNode);

        if (container != null) {
          if (container.getValue("scene_coords") != null) {

//						if (container.getValue("type").equals(DragIconType.cubic_curve.toString())) {
//							CubicCurveDemo curve = new CubicCurveDemo();
//
//							right_pane.getChildren().add(curve);
//
//							Point2D cursorPoint = container.getValue("scene_coords");
//
//							curve.relocateToPoint(
//									new Point2D(cursorPoint.getX() - 32, cursorPoint.getY() - 32)
//									);
//						}
//						else {
						ModuleIcon moduleIcon = container.getValue("type");
						DraggableNode node = new DraggableNode(moduleIcon);
						right_pane.getChildren().add(node);

						Point2D cursorPoint = container.getValue("scene_coords");

						node.relocateToPoint(
								new Point2D(cursorPoint.getX() - 32, cursorPoint.getY() - 32)
						);
//						}
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

						DraggableNode source = null;
						DraggableNode target = null;
						AnchorPane sourcePane = null;
						AnchorPane targetPane = null;
						for (Node n: right_pane.getChildren()) {
							if (n instanceof DraggableNode) {
								for (Node child : ((DraggableNode) n).getInputs().getChildren()) {
									if(child.getId() == null) {
										System.out.println("child id = null");
										continue;
									}
									if (targetId.equals(child.getId())) {
										target = (DraggableNode) n;
										targetPane = (AnchorPane) child;
									}
									if(sourceId.equals(child.getId())) {
										source = (DraggableNode) n;
										sourcePane = (AnchorPane) child;
									}
								}

								for (Node child : ((DraggableNode) n).getOutputs().getChildren()) {
									if(child.getId() == null) {
										System.out.println("child id = null");
										continue;
									}
									if (targetId.equals(child.getId())) {
										target = (DraggableNode) n;
										targetPane = (AnchorPane) child;
									}
									if(sourceId.equals(child.getId())) {
										source = (DraggableNode) n;
										sourcePane = (AnchorPane) child;
									}
								}
							}
            }

            if (source != null && target != null) {
              //TODO define number of output and input
              if (source.getModule().getOutput(source.getOutputIndexNumber(sourcePane.getId())).isPresent()) {
                //	System.out.println(container.getData());
                NodeLink link = new NodeLink();

                //add our link at the top of the rendering order so it's rendered first
                right_pane.getChildren().add(0, link);

								addLinkDeleteHandler(link);
                                Boolean connected = target.getModule().connectToInput(
                                        source.getModule().getOutput(source.getOutputIndexNumber(sourcePane.getId())).get(),
                                        target.getInputIndexNumber(targetPane.getId()));
                                if(connected) {
                                    System.out.println("connected");
                                    link.bindEnds(source, target, sourcePane, targetPane);
                                }
							}
						}
					}
						
				}

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
}
