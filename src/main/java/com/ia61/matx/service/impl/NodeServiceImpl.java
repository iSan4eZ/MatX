package com.ia61.matx.service.impl;

import com.ia61.matx.model.ui.DraggableNode;
import com.ia61.matx.service.NodeService;
import javafx.scene.Node;

/**
 * @author Dmytro Vovk
 */

public class NodeServiceImpl implements NodeService {

    @Override
    public Node getNativeNodes() {
        return new DraggableNode();
    }
}
