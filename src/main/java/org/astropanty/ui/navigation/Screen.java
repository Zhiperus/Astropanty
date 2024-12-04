package org.astropanty.ui.navigation;

import org.astropanty.App;
import org.astropanty.ui.utils.ScreenUtils;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public interface Screen {
    public Scene content();

    default Scene getBackgroundWithContent(Node content) {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: black;");

        // Add shared starry background
        ScreenUtils.addStarsWithAnimation(root, 150, 1, 3);

        // Add screen-specific content
        root.getChildren().add(content);

        return new Scene(root, App.WIDTH, App.HEIGHT);
    }
}
