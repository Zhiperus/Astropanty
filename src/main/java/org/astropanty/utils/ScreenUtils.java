package org.astropanty.utils;

import java.util.Random;

import org.astropanty.App;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class ScreenUtils {
    public static void addStarsWithAnimation(StackPane root, int count, int minSize, int maxSize) {
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            Circle star = new Circle(random.nextInt(maxSize - minSize + 1) + minSize);
            star.setFill(Color.WHITE);

            double initialX = random.nextDouble() * App.WIDTH - App.WIDTH / 2;
            double initialY = random.nextDouble() * App.HEIGHT - App.HEIGHT / 2;
            star.setTranslateX(initialX);
            star.setTranslateY(initialY);

            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(random.nextDouble() * 3 + 2), star);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0);
            fadeTransition.setCycleCount(Timeline.INDEFINITE);
            fadeTransition.setAutoReverse(true);
            fadeTransition.play();

            root.getChildren().add(star);
        }
    }
}