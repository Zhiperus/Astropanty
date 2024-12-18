package org.astropanty.ui.game.screens;

import java.util.ArrayList;
import java.util.List;

import org.astropanty.App;
import org.astropanty.data.MapLayouts;
import org.astropanty.data.ShipRepository;
import org.astropanty.ui.game.entities.Ship;
import org.astropanty.ui.game.entities.Wall;
import org.astropanty.ui.game.logic.GameTimer;
import org.astropanty.ui.navigation.Screen;
import org.astropanty.ui.navigation.ScreenController;

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
    private final int mapId;

    public final static int WINDOW_WIDTH = App.WIDTH;
    public final static int WINDOW_HEIGHT = App.HEIGHT;

    private final ScreenController screenController;
    private final Runnable navigateToMenu;

    public GameProper(ArrayList<Integer> selectedShipIds, int mapId, Runnable navigateToMenu, ScreenController screenController) {
        this.player1ShipId = selectedShipIds.get(0);
        this.player2ShipId = selectedShipIds.get(1);
        this.mapId = mapId;
        this.screenController = screenController;
        this.navigateToMenu = navigateToMenu;
    }


    @Override
    public Scene content() {
        this.root = new Group();
        this.scene = getBackgroundWithContent(root); // add starry star background to GameProper
        this.canvas = new Canvas(App.WIDTH, App.HEIGHT);
        this.root.getChildren().add(this.canvas);

        GraphicsContext gc = this.canvas.getGraphicsContext2D();

        // Fetch player 1 and player 2 attributes
        ShipRepository.ShipAttributes player1Attributes = ShipRepository.getShipAttributes(player1ShipId);
        ShipRepository.ShipAttributes player2Attributes = ShipRepository.getShipAttributes(player2ShipId);

        Ship player1Ship = new Ship(WINDOW_WIDTH*0.10, WINDOW_HEIGHT*0.20, "Player 1",
                new Image(player1Attributes.getShipImagePath(), 33, 42, false, false),
                player1Attributes.getShipSpeed(), player1Attributes.getBulletSpeed(), player1Attributes.getBulletDamage(),
                player1Attributes.getBulletImagePath());

        Ship player2Ship = new Ship(WINDOW_WIDTH*0.85, WINDOW_HEIGHT*0.80, "Player 2",
                new Image(player2Attributes.getShipImagePath(), 33, 42, false, false),
                player2Attributes.getShipSpeed(), player2Attributes.getBulletSpeed(), player2Attributes.getBulletDamage(),
                player2Attributes.getBulletImagePath());

        List<Wall> selectedMap = (mapId == 1) ? MapLayouts.getMap1Wall() : MapLayouts.getMap2Walls();

        GameTimer gameTimer = new GameTimer(gc, scene, player1Ship, player2Ship, selectedMap, navigateToMenu, screenController);
        gameTimer.start();
        gameTimer.startRace();

        return this.scene;
    }
}


