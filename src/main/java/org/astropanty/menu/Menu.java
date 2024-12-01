package org.astropanty.menu;

import org.astropanty.App;
import org.astropanty.navigation.Screen;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Menu implements Screen {
    private final Runnable navigateToGame;
    private final Runnable navigateToAbout;
    private final Runnable navigateToCredits;

    public Menu(Runnable navigateToGame, Runnable navigtaeToAbout, Runnable navigateToCredits) {
        this.navigateToGame = navigateToGame;
        this.navigateToAbout = navigtaeToAbout;
        this.navigateToCredits = navigateToCredits;
    }

    @Override
    public Scene content() {
        Text title = new Text("Astropanty");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-alignment: center;");

        Button playButton = new Button("Play Game");
        playButton.setStyle("-fx-font-size: 18px;");
        playButton.setOnAction(event -> navigateToGame.run());

        Button aboutButton = new Button("About");
        aboutButton.setStyle("-fx-font-size: 18px;");
        aboutButton.setOnAction(event -> navigateToAbout.run());

        Button creditsButton = new Button("Credits");
        creditsButton.setStyle("-fx-font-size: 18px;");
        creditsButton.setOnAction(event -> navigateToCredits.run());

        VBox layout = new VBox(20, title, playButton, aboutButton, creditsButton);
        layout.setStyle("-fx-alignment: center; -fx-padding: 50;");

        return new Scene(layout, App.WIDTH, App.HEIGHT);
    }
}
