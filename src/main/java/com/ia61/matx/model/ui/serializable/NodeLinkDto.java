package com.ia61.matx.model.ui.serializable;

import com.ia61.matx.model.ui.NodeLink;
import lombok.Getter;

@Getter
public class NodeLinkDto implements SerializableDto {

  private String sourceId;
  private String targetId;
  private String sourcePaneId;
  private String targetPaneId;

  public NodeLinkDto(NodeLink nodeLink) {
    this.sourceId = nodeLink.getSourceId();
    this.targetId = nodeLink.getTargetId();
    this.sourcePaneId = nodeLink.getSourcePaneId();
    this.targetPaneId = nodeLink.getTargetPaneId();
  }

}
