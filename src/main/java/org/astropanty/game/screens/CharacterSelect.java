package org.astropanty.game.screens;

import org.astropanty.App;
import org.astropanty.navigation.Screen;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CharacterSelect implements Screen {
    private final Runnable navigateToMenu;
    private final Runnable startGame;

    public CharacterSelect(Runnable navigateToMenu, Runnable startGame) {
        this.navigateToMenu = navigateToMenu;
        this.startGame = startGame;
    }

    @Override
    public Scene content() {
        Text title = new Text("Select your ship");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-alignment: center;");

        Button backButton = new Button("Back to Menu");
        backButton.setStyle("-fx-font-size: 18px;");
        backButton.setOnAction(event -> navigateToMenu.run());

        Button startButton = new Button("Start");
        startButton.setStyle("-fx-font-size: 18px;");
        startButton.setOnAction(event -> startGame.run());

        VBox layout = new VBox(20, title, startButton, backButton);
        layout.setStyle("-fx-alignment: center; -fx-padding: 50;");

        return new Scene(layout, App.WIDTH, App.HEIGHT);
    }

}
