package com.example.ratzilla2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RatApplication extends Application {
    private MainWindowController mainWindowController;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RatApplication.class.getResource("MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 600);
        stage.setTitle("Ratzilla2");
        stage.setScene(scene);

        mainWindowController = fxmlLoader.getController();
        stage.setOnCloseRequest(event -> mainWindowController.shutdownExecutorService());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}