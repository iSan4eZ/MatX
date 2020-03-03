package com.ia61.matx;

import com.ia61.matx.ui.RootLayout;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

//  @Override
//  public void start(Stage primaryStage) throws Exception {
//    Parent root = FXMLLoader.load(ClassLoader.getSystemClassLoader().getResource("sample.fxml"));
//    primaryStage.setTitle("Hello World");
//    primaryStage.setScene(new Scene(root, 300, 275));
//    primaryStage.show();
//  }

  @Override
  public void start(Stage primaryStage) {
//    BorderPane root = new BorderPane();

    try {
      Parent root = FXMLLoader.load(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("RootLayout.fxml")));
      Scene scene = new Scene(root,640,480);
      scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.show();

    } catch(Exception e) {
      e.printStackTrace();
    }

    //root.setCenter(new RootLayout());
  }


  public static void main(String[] args) {
    launch(args);
  }
}
