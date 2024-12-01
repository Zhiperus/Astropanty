package org.astropanty;

import java.io.IOException;

import org.astropanty.about.About;
import org.astropanty.credits.Credits;
import org.astropanty.game.Game;
import org.astropanty.menu.Menu;
import org.astropanty.navigation.Screen;
import org.astropanty.navigation.ScreenController;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    public static final int WIDTH = 960;
    public static final int HEIGHT = 540;

    private ScreenController screenController;

    private Screen aboutScreen;
    private Screen creditsScreen;
    private Screen menuScreen;
    private Screen gameScreen;

    @Override
    public void start(Stage stage) throws IOException {
        screenController = new ScreenController(stage);

        menuScreen = new Menu(
                () -> screenController.navigate(gameScreen),
                () -> screenController.navigate(aboutScreen),
                () -> screenController.navigate(creditsScreen));
        aboutScreen = new About(() -> screenController.navigate(menuScreen));
        creditsScreen = new Credits(() -> screenController.navigate(menuScreen));
        gameScreen = new Game(() -> screenController.navigate(menuScreen), screenController);

        stage.setTitle("Astropanty");
        screenController.navigate(menuScreen);
    }

    public static void main(String[] args) {
        launch();
    }
}