package com.ia61.matx.model.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class PropertyWindow extends AnchorPane {

  @FXML
  private VBox property_list;

  @FXML
  private Button cancel_btn;

  @FXML
  private Button ok_btn;

  private List<PopupField<?>> popupFields;

  public PropertyWindow(List<PopupField<?>> popupFields) {

    FXMLLoader fxmlLoader = new FXMLLoader(
        getClass().getResource("/PropertyWindow.fxml")
    );

    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    Stage stage = new Stage();
    try {
      stage.setScene(new Scene(fxmlLoader.load()));
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.popupFields = popupFields;
    List<HBox> rows = popupFields.stream()
        .map( pf -> new HBox(new Label(pf.getTitle()), pf.getControl()))
        .collect(Collectors.toList());

    property_list.getChildren().addAll(rows);
    handleOkButton();
    stage.show();
  }

  private void handleOkButton() {
    ok_btn.setOnMouseClicked((event) -> {
      for (PopupField<?> pf : popupFields) {
        switch (pf.getFieldType()) {
          case INTEGER :
            pf.setValue(Integer.valueOf(((TextField)pf.getControl()).getText()));
            break;
          case FLOAT:
            pf.setValue(Float.valueOf(((TextField)pf.getControl()).getText()));
            break;
          case BOOLEAN:
            pf.setValue(((CheckBox)pf.getControl()).isSelected());
            break;
          case BINARY_STRING:
            pf.setValue(((TextField)pf.getControl()).getText());
            break;
        }
      }
    });
  }
}
