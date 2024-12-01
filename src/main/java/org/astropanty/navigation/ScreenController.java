package org.astropanty.navigation;

import javafx.stage.Stage;

public class ScreenController {
    private final Stage stage;
    private Screen currentScreen;

    public ScreenController(Stage stage) {
        this.stage = stage;
    }

    public void navigate(Screen screen) {
        if (currentScreen == screen)
            return;

        this.currentScreen = screen;
        stage.setScene(screen.content());
        stage.show();
    }
}
