package org.astropanty.ui.game.screens;

import java.util.ArrayList;

import org.astropanty.App;
import org.astropanty.data.ShipImageRepository;
import org.astropanty.ui.game.entities.Ship;
import org.astropanty.ui.game.logic.GameTimer;
import org.astropanty.ui.navigation.Screen;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GameProper implements Screen {

    private Scene scene;
    private Group root;
    private Canvas canvas;

    private final int player1ShipId;
    private final int player2ShipId;

    public final static int WINDOW_WIDTH = App.WIDTH;
    public final static int WINDOW_HEIGHT = App.HEIGHT;

    public GameProper(ArrayList<Integer> selectedShipIds) {
        this.player1ShipId = selectedShipIds.get(0);
        this.player2ShipId = selectedShipIds.get(1);
    }

    @Override
    public Scene content() {
        this.root = new Group();
        this.scene = getBackgroundWithContent(root); // add starry star background to GameProper
        this.canvas = new Canvas(App.WIDTH, App.HEIGHT);
        this.root.getChildren().add(this.canvas);

        GraphicsContext gc = this.canvas.getGraphicsContext2D();

        Ship player1Ship = new Ship(50, WINDOW_HEIGHT / 2, "Ship 1",
                new Image(ShipImageRepository.getShipImagePath(player1ShipId), 33, 42, false, false));
        Ship player2Ship = new Ship(WINDOW_WIDTH - 100, WINDOW_HEIGHT / 2, "Ship 1",
                new Image(ShipImageRepository.getShipImagePath(player2ShipId), 33, 42, false, false));

        GameTimer gameTimer = new GameTimer(gc, scene, player1Ship, player2Ship);
        gameTimer.start();
        gameTimer.startRace();

        return this.scene;
    }
}
