package org.astropanty.ui.game.screens;

import org.astropanty.ui.navigation.Screen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.astropanty.ui.components.Button;

/**
 * Provides a screen for showing the winner or a draw result.
 */
public class WinningScreen implements Screen {
    private final Runnable navigateToMenu;
    private final String winner;
    private final Image ship1Image;
    private final Image ship2Image;

    public WinningScreen(Runnable navigateToMenu, String winner, Image ship1Image, Image ship2Image) {
        this.navigateToMenu = navigateToMenu;
        this.winner = winner;
        this.ship1Image = ship1Image;
        this.ship2Image = ship2Image;
    }

    @Override
    public Scene content() {
        // Title text
        Text title = new Text(winner.equalsIgnoreCase("draw") ? "It's a Draw!" : "Winner!");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-fill: white;");

        // Winner or draw message
        Text winnerText = new Text(winner.equalsIgnoreCase("draw") ? "No winner this time!" : winner + " has won!");
        winnerText.setStyle("-fx-font-size: 24px; -fx-fill: white;");

        // Ship image views
        HBox shipImagesBox = new HBox(20);
        shipImagesBox.setAlignment(Pos.CENTER);

        if (winner.equalsIgnoreCase("draw") && ship1Image != null && ship2Image != null) {
            ImageView ship1ImageView = createShipImageView(ship1Image);
            ImageView ship2ImageView = createShipImageView(ship2Image);
            shipImagesBox.getChildren().addAll(ship1ImageView, ship2ImageView);
        } else if (ship1Image != null) {
            ImageView shipImageView = createShipImageView(ship1Image);
            shipImagesBox.getChildren().add(shipImageView);
        }

        // Navigation button
        Button backButton = new Button("Back to Menu", navigateToMenu);

        // Layouts
        HBox actionButtons = new HBox(20, backButton);
        actionButtons.setAlignment(Pos.CENTER);

        VBox mainLayout = new VBox(40, title, shipImagesBox, winnerText, actionButtons);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);

        // Return the scene with background
        return getBackgroundWithContent(mainLayout);
    }

    /**
     * Utility method to create an ImageView for a ship.
     */
    private ImageView createShipImageView(Image shipImage) {
        ImageView shipImageView = new ImageView(shipImage);
        shipImageView.setFitWidth(200);
        shipImageView.setPreserveRatio(true);
        return shipImageView;
    }
}
