package org.astropanty.credits;

import org.astropanty.App;
import org.astropanty.navigation.Screen;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Credits implements Screen {
    private final Runnable navigateToMenu;

    public Credits(Runnable navigateToMenu) {
        this.navigateToMenu = navigateToMenu;
    }

    @Override
    public Scene content() {
        Text title = new Text("Credits");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-alignment: center;");

        Text creditsText = new Text(
                "Game Design: Mga Kupal\n" +
                        "Programming: Iorie Alen Chua\n\t\t\t\tEunel Jacob Joyosa\n\t\t\t\tRoberto Neil Santos\n" +
                        "Art and Graphics: Eunel Jacob Joyosa\n" +
                        "Music and Sound Effects: Mga Kupal\n\n" +
                        "Thank you for playing our game!");
        creditsText.setStyle("-fx-font-size: 16px; -fx-text-alignment: center;");

        Button backButton = new Button("Back to Menu");
        backButton.setStyle("-fx-font-size: 18px;");
        backButton.setOnAction(event -> navigateToMenu.run());

        VBox layout = new VBox(20, title, creditsText, backButton);
        layout.setStyle("-fx-alignment: center; -fx-padding: 50;");

        return new Scene(layout, App.WIDTH, App.HEIGHT);
    }

}
