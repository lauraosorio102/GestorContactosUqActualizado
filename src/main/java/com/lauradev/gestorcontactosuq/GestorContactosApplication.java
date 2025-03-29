package com.lauradev.gestorcontactosuq;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GestorContactosApplication extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(GestorContactosApplication.class.getResource("/gestor-contactos.fxml"));
        Parent parent = fxmlLoader.load();


        Scene scene = new Scene(parent);
        stage.setTitle("Gestor de Contactos");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}