package com.ia61.matx.service.impl;

import com.ia61.matx.model.ui.DraggableNode;
import com.ia61.matx.service.NodeService;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;

/**
 * @author Dmytro Vovk
 */

public class NodeServiceImpl implements NodeService {

    @Override
    public Node getNativeNodes() {
        // определяем корневой узел
        TreeItem<String> rootTreeNode = new TreeItem<String>("Languages");

        // определяем набор вложенных узлов
        TreeItem<String> germanics = new TreeItem<String>("Germanic");
        germanics.getChildren().add(new TreeItem<String>("German"));
        germanics.getChildren().add(new TreeItem<String>("English"));

        TreeItem<String> romans = new TreeItem<String>("Roman");
        romans.getChildren().add(new TreeItem<String>("French"));
        romans.getChildren().add(new TreeItem<String>("Spanish"));
        romans.getChildren().add(new TreeItem<String>("Italian"));

        // добавляем узлы в корневой узел
        rootTreeNode.getChildren().add(germanics);
        rootTreeNode.getChildren().add(romans);

        // устанавливаем корневой узел для TreeView
        //elementsTree = new TreeView<String>(rootTreeNode);
        DraggableNode node = new DraggableNode();
        node.setId("iddd");
        return node;
    }
}
