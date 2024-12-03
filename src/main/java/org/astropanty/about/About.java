package org.astropanty.about;

import org.astropanty.components.Button;
import org.astropanty.navigation.Screen;

import javafx.scene.Scene;
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
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-alignment: center; -fx-fill: white;");

        Text movementInstructions = new Text("Use W, A, D or Up, Left, Right to move");
        movementInstructions.setStyle("-fx-font-size: 18px; -fx-fill: white;");

        Text fireInstructions = new Text("Press 'Space' or 'Enter' to fire");
        fireInstructions.setStyle("-fx-font-size: 18px; -fx-fill: white;");

        Text mouseInstructions = new Text("Rotate using A/D or Left/Right Arrow to aim");
        mouseInstructions.setStyle("-fx-font-size: 18px; -fx-fill: white;");

        Button backButton = new Button("Back to Menu", navigateToMenu);

        VBox layout = new VBox(20, title, movementInstructions, mouseInstructions, fireInstructions, backButton);
        layout.setStyle("-fx-alignment: center; -fx-padding: 50;");

        return getBackgroundWithContent(layout);
    }
}
