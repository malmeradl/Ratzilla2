package com.example.ratzilla2;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainWindowController {
    @FXML
    private Label energyLabel;
    @FXML
    private Label satietyLabel;
    @FXML
    private Button energyButton;
    @FXML
    private Button satietyButton;
    @FXML
    private ImageView imageRat;
    Rat rat;
    private ScheduledExecutorService executorService;
    Image defaultRat;
    Image eatRat;
    Image hungryRat;
    Image deathRat;
    Image sleepRat;
    Image tiredRat;


    @FXML
    public void initialize() {
        rat = new Rat("Christofer");
        initializeImages();
        createThreads();
    }


    private void createThreads() {
        Runnable energyDecrease =
                () -> {
                    rat.decreaseEnergy();
                };
        Runnable satietyDecrease =
                () -> {
                    rat.decreaseSatiety();
                };
        Runnable updateIndicators =
                () -> {
                    Platform.runLater(() -> satietyLabel.setText(Integer.toString(rat.getSatiety())));
                    Platform.runLater(() -> energyLabel.setText(Integer.toString(rat.getEnergy())));
                };
        Runnable updateImage =
                () -> {
                    Platform.runLater(() -> imageRat.setImage(checkImageByIndicators()));
                };


        executorService = Executors.newScheduledThreadPool(4);

        // Запуск потоков обновления параметров
        executorService.scheduleAtFixedRate(updateIndicators, 0, 1, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(energyDecrease, 0, 1, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(satietyDecrease, 0, 3, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(updateImage, 0, 1, TimeUnit.SECONDS);
    }


    public void shutdownExecutorService() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
            System.out.println("closed");
        }
    }

    private void initializeImages() {
        //передается поток с файлом изображения
        sleepRat = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/ratzilla2/images/sleep.jpg")));
        defaultRat = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/ratzilla2/images/default.jpg")));
        eatRat = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/ratzilla2/images/eat.jpg")));
        hungryRat = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/ratzilla2/images/hungry.jpg")));
        deathRat = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/ratzilla2/images/smert.jpg")));
        tiredRat = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/ratzilla2/images/tired.jpg")));
    }

    @FXML
    protected void onEnergyButtonClick() {
        rat.setEnergy(rat.getEnergy() + 3);
        imageRat.setImage(sleepRat);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(energyButton.styleProperty(), "-fx-background-color: #ffb035;")),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(energyButton.styleProperty(), "-fx-background-color: #ffce82;"))
        );
        timeline.play();
    }

    @FXML
    protected void onSatietyButtonClick() {
        rat.setSatiety(rat.getSatiety() + 4);
        imageRat.setImage(eatRat);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(satietyButton.styleProperty(), "-fx-background-color: #ffb035;")),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(satietyButton.styleProperty(), "-fx-background-color: #ffce82;"))
        );
        timeline.play();
    }

    public Image checkImageByIndicators() {
        if (rat.getEnergy() < 20) {
            return tiredRat;
        } else if (rat.getSatiety() < 20) {
            return hungryRat;
        } else {
            return defaultRat;
        }
    }

}