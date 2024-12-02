package org.astropanty.game.screens;

import org.astropanty.components.Button;
import org.astropanty.navigation.Screen;

import javafx.scene.Scene;
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
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-alignment: center; -fx-fill: white;");

        Button backButton = new Button("Back to Menu", navigateToMenu);
        Button startButton = new Button("Start", startGame);

        VBox layout = new VBox(20, title, startButton, backButton);
        layout.setStyle("-fx-alignment: center; -fx-padding: 50;");

        return getBackgroundWithContent(layout);
    }

}
