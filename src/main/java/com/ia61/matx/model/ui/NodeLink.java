package com.ia61.matx.model.ui;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.When;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.CubicCurve;

import java.io.IOException;
import java.util.UUID;

import static com.ia61.matx.constants.UIConstants.DRAGGABLE_NODE_HEADER_HEIGHT;

public class NodeLink extends AnchorPane {

  @FXML
  CubicCurve node_link;

  private final DoubleProperty mControlOffsetX = new SimpleDoubleProperty();
  private final DoubleProperty mControlOffsetY = new SimpleDoubleProperty();
  private final DoubleProperty mControlDirectionX1 = new SimpleDoubleProperty();
  private final DoubleProperty mControlDirectionY1 = new SimpleDoubleProperty();
  private final DoubleProperty mControlDirectionX2 = new SimpleDoubleProperty();
  private final DoubleProperty mControlDirectionY2 = new SimpleDoubleProperty();

  private String sourceId;
  private String targetId;
  private String sourcePaneId;
  private String targetPaneId;
  private Boolean toDelete = false;

  public NodeLink() {

    FXMLLoader fxmlLoader = new FXMLLoader(
        getClass().getResource("/NodeLink.fxml")
    );

    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();

    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }

    //provide a universally unique identifier for this object
    setId(UUID.randomUUID().toString());
  }

  @FXML
  private void initialize() {

    mControlOffsetX.set(100.0);
    mControlOffsetY.set(50.0);

    mControlDirectionX1.bind(new When(
        node_link.startXProperty().greaterThan(node_link.endXProperty()))
        .then(-1.0).otherwise(1.0));

    mControlDirectionX2.bind(new When(
        node_link.startXProperty().greaterThan(node_link.endXProperty()))
        .then(1.0).otherwise(-1.0));

    node_link.controlX1Property().bind(
        Bindings.add(
            node_link.startXProperty(), mControlOffsetX.multiply(mControlDirectionX1)
        )
    );

    node_link.controlX2Property().bind(
        Bindings.add(
            node_link.endXProperty(), mControlOffsetX.multiply(mControlDirectionX2)
        )
    );

    node_link.controlY1Property().bind(
        Bindings.add(
            node_link.startYProperty(), mControlOffsetY.multiply(mControlDirectionY1)
        )
    );

    node_link.controlY2Property().bind(
        Bindings.add(
            node_link.endYProperty(), mControlOffsetY.multiply(mControlDirectionY2)
        )
    );
  }

  public String getSourceId() {
    return sourceId;
  }

  public String getTargetId() {
    return targetId;
  }

  public String getSourcePaneId() {
    return sourcePaneId;
  }

  public String getTargetPaneId() {
    return targetPaneId;
  }

  public CubicCurve getNode_link() {
    return node_link;
  }

  public void setStart(Point2D startPoint) {

    node_link.setStartX(startPoint.getX());
    node_link.setStartY(startPoint.getY());
  }

  public void setEnd(Point2D endPoint) {

    node_link.setEndX(endPoint.getX());
    node_link.setEndY(endPoint.getY());
  }


  public void bindEnds(DraggableNode source, DraggableNode target, AnchorPane sourcePane, AnchorPane targetPane) {

    sourceId = source.getId();
    targetId = target.getId();
    sourcePaneId = sourcePane.getId();
    targetPaneId = targetPane.getId();

    node_link.startXProperty().bind(
        Bindings.add(source.layoutXProperty().add(getExtraX(source, sourcePane)),
            sourcePane.getLayoutX()));

    final double sourceOutputHeight = source.getOutputs().getPrefHeight() / source.getOutputs().getChildren().size();
    node_link.startYProperty().bind(
        Bindings.add(source.layoutYProperty()
                .add(DRAGGABLE_NODE_HEADER_HEIGHT + sourceOutputHeight * source.getOutputIndexNumber(sourcePaneId)),
            sourceOutputHeight / 2));

    node_link.endXProperty().bind(
        Bindings.add(target.layoutXProperty().add(getExtraX(target, targetPane)),
            targetPane.getLayoutX()));

    final double targetInputHeight = target.getInputs().getPrefHeight() / target.getInputs().getChildren().size();
    node_link.endYProperty().bind(
        Bindings.add(target.layoutYProperty()
                .add(DRAGGABLE_NODE_HEADER_HEIGHT + targetInputHeight * target.getInputIndexNumber(targetPaneId)),
            targetInputHeight / 2));

    source.registerLink(getId());
    target.registerLink(getId());
    target.addTakenInput(targetPane.getId(), sourcePane.getId());
  }

  private int getExtraX(DraggableNode node, AnchorPane pane) {
    for (Node left : node.getInputs().getChildren()) {
      if (pane.getId().equals(left.getId())) {
        return 5; //additional X
      }
    }
    for (Node right : node.getOutputs().getChildren()) {
      if (pane.getId().equals(right.getId())) {
        return 75; //additional X
      }
    }
    return 0;
  }

  public Boolean getToDelete() {
    return toDelete;
  }

  public void setToDelete(Boolean toDelete) {
    this.toDelete = toDelete;
  }
}