package com.ia61.matx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    TreeView<String> elementsTree;

    public Controller() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillElementsTree();
    }

    private void fillElementsTree () {
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
        elementsTree.setRoot(rootTreeNode);
    }
}
