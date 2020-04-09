package com.ia61.matx.model.ui.serializable;

import com.ia61.matx.model.ui.DraggableNode;
import com.ia61.matx.model.ui.enums.ModuleIcon;
import com.ia61.matx.module.Module;
import javafx.scene.Node;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DraggableNodeDto implements SerializableDto {

  private ModuleIcon moduleIcon;
  private Double xPosition;
  private Double yPosition;
  private String id;
  private List<String> inputIdList;
  private List<String> outputIdList;
  private Module module;

  public DraggableNodeDto(DraggableNode draggableNode) {
    this.moduleIcon = draggableNode.getModuleIcon();
    this.xPosition = draggableNode.getLayoutX() + 89;
    this.yPosition = draggableNode.getLayoutY() + 46;
    this.id = draggableNode.getId();
    this.inputIdList = draggableNode.getInputs().getChildren().stream()
        .map(Node::getId)
        .collect(Collectors.toList());
    this.outputIdList = draggableNode.getOutputs().getChildren().stream()
        .map(Node::getId)
        .collect(Collectors.toList());
    this.module = draggableNode.getModule();
  }

  public DraggableNode getDraggableNode() {
    return new DraggableNode(getModuleIcon(), getModule(), getId(), inputIdList,
        outputIdList);
  }

}
