package org.astropanty.about;

import org.astropanty.App;
import org.astropanty.navigation.Screen;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class About implements Screen {
    private final Runnable navigateToMenu;

    public About(Runnable navigateToMenu) {
        this.navigateToMenu = navigateToMenu;
    }

    @Override
    public Scene content() {
        Text title = new Text("Game Instructions");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-alignment: center;");

        Text movementInstructions = new Text("Use W, A, S, D to move");
        movementInstructions.setStyle("-fx-font-size: 18px;");

        Text fireInstructions = new Text("Press 'P' to fire");
        fireInstructions.setStyle("-fx-font-size: 18px;");

        Text mouseInstructions = new Text("Use the mouse to aim");
        mouseInstructions.setStyle("-fx-font-size: 18px;");

        Button backButton = new Button("Back to Menu");
        backButton.setStyle("-fx-font-size: 18px;");
        backButton.setOnAction(event -> navigateToMenu.run());

        VBox layout = new VBox(20, title, movementInstructions, mouseInstructions, fireInstructions, backButton);
        layout.setStyle("-fx-alignment: center; -fx-padding: 50;");

        return new Scene(layout, App.WIDTH, App.HEIGHT);
    }
}
