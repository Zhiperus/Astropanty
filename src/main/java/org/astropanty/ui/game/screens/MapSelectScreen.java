package org.astropanty.ui.game.screens;

import java.util.function.Consumer;
import org.astropanty.ui.navigation.Screen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.astropanty.ui.components.Button;

/**
 * Provides a screen for selecting a map before starting the game.
 */
public class MapSelectScreen implements Screen{
    private final Runnable navigateToMenu;
    private final Consumer<Integer> startGameWithMap;

    public MapSelectScreen(Runnable navigateToMenu, Consumer<Integer> startGameWithMap){
        this.navigateToMenu = navigateToMenu;
        this.startGameWithMap = startGameWithMap;
    }

    @Override
    public Scene content() {
        Text title = new Text("Select You Map");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: white;");

        // Map 1 Button
        Button map1Button = new Button("The Wrap", () -> handleMapSelection(1));
        map1Button.setPrefSize(200, 50);

        // Map 2 Button
        Button map2Button = new Button("Funky Cubes", () -> handleMapSelection(2));
        map2Button.setPrefSize(200, 50);

        // Navigation Buttons
        Button backButton = new Button("Back to Menu", navigateToMenu);

        HBox mapButtons = new HBox(40, map1Button, map2Button);
        mapButtons.setAlignment(Pos.CENTER);

        HBox actionButtons = new HBox(20, backButton);
        actionButtons.setAlignment(Pos.CENTER);

        VBox mainLayout = new VBox(40, title, mapButtons, actionButtons);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);

        return getBackgroundWithContent(mainLayout);
    }

    /**
     * Handles selection of a map.
     *
     * @param mapId The ID of the selected map (1 or 2).
     */
    private void handleMapSelection(int mapId){
        System.out.println("Map " + mapId + " selected.");
        startGameWithMap.accept(mapId);
    }
}
