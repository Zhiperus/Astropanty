package org.astropanty.game;

import org.astropanty.game.screens.CharacterSelect;
import org.astropanty.game.screens.GameProper;
import org.astropanty.navigation.Screen;
import org.astropanty.navigation.ScreenController;

import javafx.scene.Scene;

public class Game implements Screen {
    private final Runnable navigateToMenu;
    private final ScreenController screenController;

    private Screen characterSelectScreen;
    private Screen gameProper;

    public Game(Runnable navigateToMenu, ScreenController screenController) {
        this.navigateToMenu = navigateToMenu;
        this.screenController = screenController;
    }

    @Override
    public Scene content() {
        characterSelectScreen = new CharacterSelect(navigateToMenu, () -> screenController.navigate(gameProper));
        gameProper = new GameProper();

        // return character select as initial screen
        return characterSelectScreen.content();
    }
}
