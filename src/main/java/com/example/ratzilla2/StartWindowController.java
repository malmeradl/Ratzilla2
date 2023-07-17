package com.example.ratzilla2;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;


public class StartWindowController {
    @FXML
    private Label bestNick;
    @FXML
    private Label bestScore;
    @FXML
    private TextField newNick;
    @FXML
    private Button playButton;

    @FXML
    private void onPlayButtonClick() {
        String playerName = newNick.getText().trim();
        if (!playerName.isEmpty()) {
            openMainWindow(playerName);
        }
        else{
            newNick.setAccessibleText("Введите имя крыски.");
        }
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(playButton.styleProperty(), "-fx-background-color: #ffb035;")),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(playButton.styleProperty(), "-fx-background-color: #ffce82;"))
        );
        timeline.play();
    }

    private void openMainWindow(String name) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
            Parent root = loader.load();
            MainWindowController controller = loader.getController();
            controller.initialize(name);

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 400, 600));
            stage.setTitle("Main Window");
            stage.setOnCloseRequest(event -> controller.shutdownExecutorService());
            stage.show();

            // Закрываем окно startWindow
            Stage startWindowStage = (Stage) newNick.getScene().getWindow();
            startWindowStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
