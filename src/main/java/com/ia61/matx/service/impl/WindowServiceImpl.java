package com.ia61.matx.service.impl;

import com.ia61.matx.service.WindowService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Dmytro Vovk
 */

public class WindowServiceImpl implements WindowService {

    @Override
    public void buildWindow(String windowName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
//            fxmlLoader.setLocation(getClass().getResource("sample.fxml"));
//            /*
//             * if "fx:controller" is not set in fxml
//             * fxmlLoader.setController(NewWindowController);
//             */
//            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
//            Stage stage = new Stage();
//            stage.setTitle("Chart");
//            stage.setScene(scene);
//            stage.show();

            Parent root = FXMLLoader.load(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(windowName)));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ignored) {
        }
    }
}
