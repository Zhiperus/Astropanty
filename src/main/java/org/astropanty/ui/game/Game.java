package org.astropanty.ui.game;

import org.astropanty.ui.game.screens.CharacterSelect;
import org.astropanty.ui.game.screens.GameProper;
import org.astropanty.ui.navigation.Screen;
import org.astropanty.ui.navigation.ScreenController;

import javafx.scene.Scene;

public class Game implements Screen {
    private final Runnable navigateToMenu;
    private final ScreenController screenController;

    private Screen characterSelectScreen;

    public Game(Runnable navigateToMenu, ScreenController screenController) {
        this.navigateToMenu = navigateToMenu;
        this.screenController = screenController;
    }

    @Override
    public Scene content() {
        characterSelectScreen = new CharacterSelect(navigateToMenu,
                (selectedShipIds) -> screenController.navigate(new GameProper(selectedShipIds)));

        // return character select as initial screen
        return characterSelectScreen.content();
    }
}
