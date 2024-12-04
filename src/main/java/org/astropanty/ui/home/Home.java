package org.astropanty.ui.home;

import org.astropanty.ui.home.about.About;
import org.astropanty.ui.home.credits.Credits;
import org.astropanty.ui.home.menu.Menu;
import org.astropanty.ui.navigation.Screen;
import org.astropanty.ui.navigation.ScreenController;

import javafx.scene.Scene;

public class Home implements Screen {
    private final Runnable navigateToGame;
    private final ScreenController screenController;

    Screen menuScreen;
    Screen aboutScreen;
    Screen creditsScreen;

    public Home(Runnable navigateToGame, ScreenController screenController) {
        this.navigateToGame = navigateToGame;
        this.screenController = screenController;
    }

    @Override
    public Scene content() {
        menuScreen = new Menu(
                navigateToGame,
                () -> screenController.navigate(aboutScreen),
                () -> screenController.navigate(creditsScreen));
        aboutScreen = new About(() -> screenController.navigate(menuScreen));
        creditsScreen = new Credits(() -> screenController.navigate(menuScreen));

        return menuScreen.content();
    }

}
