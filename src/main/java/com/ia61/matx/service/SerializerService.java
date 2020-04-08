package com.ia61.matx.service;

import com.ia61.matx.model.ui.DraggableNode;
import com.ia61.matx.model.ui.NodeLink;
import com.ia61.matx.model.ui.RootLayout;
import com.ia61.matx.model.ui.serializable.DraggableNodeDto;
import com.ia61.matx.model.ui.serializable.NodeLinkDto;
import com.ia61.matx.model.ui.serializable.SerializableDto;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class SerializerService {

  private static RootLayout rootLayout;

  public static void setRootLayout(RootLayout rootLayout) {
    SerializerService.rootLayout = rootLayout;
  }

  public static void save(String path) {
    final List<DraggableNodeDto> draggableNodeDtoList = new ArrayList<>();
    final List<NodeLinkDto> nodeLinkDtoList = new ArrayList<>();

    rootLayout.getRight_pane().getChildren().forEach(child -> {
      if (child instanceof DraggableNode) {
        draggableNodeDtoList.add(new DraggableNodeDto((DraggableNode) child));
      } else if (child instanceof NodeLink) {
        nodeLinkDtoList.add(new NodeLinkDto((NodeLink) child));
      }
    });

    List<List<? extends SerializableDto>> objectToSerialize = Arrays.asList(draggableNodeDtoList, nodeLinkDtoList);

    try (FileOutputStream file = new FileOutputStream(path);
        ObjectOutputStream out = new ObjectOutputStream(file)) {
      out.writeObject(objectToSerialize);
      log.info("Successfully saved to file!");
    } catch (IOException e) {
      log.error("Error saving file", e);
    }
  }

  public static void load(String path) {
    Platform.runLater(
        () -> {
          rootLayout.clearRightPane();
          try (FileInputStream file = new FileInputStream(path);
              ObjectInputStream in = new ObjectInputStream(file)) {
            List<List<? extends SerializableDto>> deserealizedObjects = (List<List<? extends SerializableDto>>) in
                .readObject();
            final List<DraggableNodeDto> draggableNodeDtoList = (List<DraggableNodeDto>) deserealizedObjects.get(0);
            final List<NodeLinkDto> nodeLinkDtoList = (List<NodeLinkDto>) deserealizedObjects.get(1);
            draggableNodeDtoList.forEach(draggableNodeDto -> rootLayout
                .addDraggableNode(draggableNodeDto.getDraggableNode(),
                    new Point2D(draggableNodeDto.getXPosition(), draggableNodeDto.getYPosition())));

            //<id, node>
            final Map<String, DraggableNode> draggableNodeMap = rootLayout.getRight_pane().getChildren().stream()
                .filter(node -> node instanceof DraggableNode)
                .map(node -> (DraggableNode) node)
                .collect(Collectors.toMap(DraggableNode::getId, Function.identity()));

            nodeLinkDtoList.forEach(nodeLinkDto -> {
              final Optional<DraggableNode> optionalSourceNode = Optional
                  .ofNullable(draggableNodeMap.get(nodeLinkDto.getSourceId()));
              final Optional<DraggableNode> optionalTargetNode = Optional
                  .ofNullable(draggableNodeMap.get(nodeLinkDto.getTargetId()));

              if (optionalSourceNode.isPresent() && optionalTargetNode.isPresent()) {
                final DraggableNode sourceNode = optionalSourceNode.get();
                final DraggableNode targetNode = optionalTargetNode.get();

                final Optional<AnchorPane> optionalSourcePane = sourceNode.getOutputs().getChildren().stream()
                    .filter(node -> node instanceof AnchorPane && nodeLinkDto.getSourcePaneId().equals(node.getId()))
                    .map(node -> (AnchorPane) node)
                    .findFirst();
                final Optional<AnchorPane> optionalTargetPane = targetNode.getInputs().getChildren().stream()
                    .filter(node -> node instanceof AnchorPane && nodeLinkDto.getTargetPaneId().equals(node.getId()))
                    .map(node -> (AnchorPane) node)
                    .findFirst();

                if (optionalSourcePane.isPresent() && optionalTargetPane.isPresent()) {
                  rootLayout.addNodeLink(sourceNode, targetNode, optionalSourcePane.get(), optionalTargetPane.get());
                }
              }
            });
            log.info("Successfully loaded from file!");
          } catch (IOException | ClassNotFoundException e) {
            log.error("Error loading from file", e);
          }
        });
  }

}
