package Glowne;

import bazadanych.DatabaseControll;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoaderGlowneOkno extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GlowneOkno.fxml"));
        primaryStage.setTitle("Projekt Java");
        primaryStage.setScene(new Scene(root, 666, 467));
        primaryStage.show();
        DatabaseControll.getInstance();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

