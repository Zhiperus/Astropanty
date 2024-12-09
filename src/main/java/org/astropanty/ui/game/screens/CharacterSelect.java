package org.astropanty.ui.game.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.astropanty.data.ShipRepository;
import org.astropanty.ui.components.Button;
import org.astropanty.ui.navigation.Screen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CharacterSelect implements Screen {
    private final Runnable navigateToMenu;
    private final Consumer<ArrayList<Integer>> startGame;

    private final ArrayList<Integer> playerSelections = new ArrayList<>();
    private final ImageView player1Selection = new ImageView();
    private final ImageView player2Selection = new ImageView();

    public CharacterSelect(Runnable navigateToMenu, Consumer<ArrayList<Integer>> startGame) {
        this.navigateToMenu = navigateToMenu;
        this.startGame = startGame;

        setupImageView(player1Selection);
        setupImageView(player2Selection);
    }

    @Override
    public Scene content() {
        Text title = new Text("Select Your Ship");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: white;");

        HBox shipSelection = createShipSelectionButtons();

        HBox splitScreen = new HBox(40, createPlayerPanel("Player 1", player1Selection),
                createPlayerPanel("Player 2", player2Selection));
        splitScreen.setAlignment(Pos.CENTER);

        Button backButton = new Button("Back to Menu", navigateToMenu);
        Button startButton = new Button("Start", () -> startGame.accept(playerSelections));
        HBox actionButtons = new HBox(20, backButton, startButton);
        actionButtons.setAlignment(Pos.CENTER);

        VBox mainLayout = new VBox(40, title, splitScreen, shipSelection, actionButtons);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);

        return getBackgroundWithContent(mainLayout);
    }

    private HBox createShipSelectionButtons() {
        HBox shipButtons = new HBox(20);
        shipButtons.setAlignment(Pos.CENTER);

        List<String> shipImages = ShipRepository.getAllShipImages();
        for (int i = 0; i < shipImages.size(); i++) {
            int shipId = i;
            String shipImagePath = shipImages.get(shipId);

            ImageView shipImageView = new ImageView(new Image(shipImagePath));
            shipImageView.setFitWidth(50);
            shipImageView.setFitHeight(50);
            shipImageView.setPreserveRatio(true);

            javafx.scene.control.Button shipButton = new javafx.scene.control.Button();
            shipButton.setGraphic(shipImageView);
            shipButton.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");

            shipButton.setOnAction(event -> handleShipSelection(shipId, shipImagePath));
            shipButtons.getChildren().add(shipButton);
        }

        return shipButtons;
    }

    private void handleShipSelection(int shipId, String imagePath) {
        Image shipImage = new Image(imagePath);
        playerSelections.add(shipId);

        if (player1Selection.getImage() == null) {
            player1Selection.setImage(shipImage);
            System.out.println("Player 1 selected: " + imagePath);
        } else if (player2Selection.getImage() == null) {
            player2Selection.setImage(shipImage);
            System.out.println("Player 2 selected: " + imagePath);
        } else {
            System.out.println("Both players have already selected ships!");
        }
    }

    private VBox createPlayerPanel(String playerName, ImageView imageView) {
        Text playerTitle = new Text(playerName);
        playerTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: white;");

        VBox playerPanel = new VBox(20, playerTitle, imageView);
        playerPanel.setAlignment(Pos.CENTER);
        playerPanel.setPrefSize(400, 400);
        playerPanel.setStyle("-fx-border-color: white; -fx-padding: 10;");

        return playerPanel;
    }

    private void setupImageView(ImageView imageView) {
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(false);
        imageView.setSmooth(false);
        imageView.setImage(null);
    }
}
