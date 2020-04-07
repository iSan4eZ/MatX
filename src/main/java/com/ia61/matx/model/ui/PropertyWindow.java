package com.ia61.matx.model.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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

  private Stage mStage = null;

  private List<PopupField<?>> popupFields;

  public PropertyWindow(String title, List<PopupField<?>> popupFields) {

    FXMLLoader fxmlLoader = new FXMLLoader(
        getClass().getResource("/PropertyWindow.fxml")
    );

    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    mStage = new Stage();
    try {
      mStage.setScene(new Scene(fxmlLoader.load()));
      mStage.setTitle(title);
      mStage.setMinHeight(340f);
      mStage.setMinWidth(420f);
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.popupFields = popupFields;
    List<HBox> rows = popupFields.stream()
        .map(PopupField::getHBox)
        .collect(Collectors.toList());

    property_list.getChildren().addAll(rows);
    handleWindowButtons();
    mStage.show();
  }

  private void handleWindowButtons() {
    // 'OK' button
    ok_btn.setOnMouseClicked((event) -> {
      for (PopupField<?> pf : popupFields) {
        switch (pf.getFieldType()) {
          case INTEGER :
            pf.setValue(Integer.valueOf(((TextField)pf.getControl()).getText()));
            break;
          case FLOAT:
            pf.setValue(Float.valueOf(((TextField)pf.getControl()).getText()));
            break;
          case LONG:
            pf.setValue(Long.valueOf(((TextField) pf.getControl()).getText()));
            break;
          case BOOLEAN:
            pf.setValue(((CheckBox) pf.getControl()).isSelected());
            break;
          case BINARY_STRING:
            pf.setValue(((TextField) pf.getControl()).getText());
            break;
          case ENUM:
            pf.setValue(((ComboBox) pf.getControl()).getValue());
        }
      }

      mStage.close();
    });

    // 'Cancel' button
    cancel_btn.setOnMouseClicked((event) ->
      mStage.close()
    );
  }
}
