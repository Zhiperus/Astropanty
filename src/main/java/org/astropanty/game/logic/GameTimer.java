package org.astropanty.game.logic;

import java.util.HashSet; // For handling the main game loop
import java.util.Iterator;
import java.util.List; // For rendering the game graphics
import java.util.Set; // For handling images of the Ships and pedestrians

import org.astropanty.game.entities.Projectile;
import org.astropanty.game.entities.Ship; // For setting the colors for the game elements
import org.astropanty.game.screens.GameProper;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class GameTimer extends AnimationTimer {
    private final GraphicsContext gc; // GraphicsContext for rendering game elements
    private final Scene scene;
    private final Ship myShip; // Two Ship objects representing the players
    private final Ship yourShip; // Two Ship objects representing the players
    private final Thread myShipThread;
    private final Thread yourShipThread;
    private final Set<KeyCode> activeKeys = new HashSet<>();

    // Constructor initializing Ships, pedestrians, and threads
    public GameTimer(GraphicsContext gc, Scene scene, GameProper game) {
        this.gc = gc; // Initialize GraphicsContext
        this.scene = scene;

        // Create Ship and pedestrian objects with initial positions and images
        this.myShip = new Ship(10, 300, "myShip", new Image(
                getClass().getResource("/org/astropanty/yourShip.png").toExternalForm(), 40, 40, false, false));
        this.yourShip = new Ship(490, 300, "yourShip",
                new Image(getClass().getResource("/org/astropanty/myShip.png").toExternalForm(), 40, 40, false, false));

        // Create threads for each Ship and pedestrian (since Ships and pedestrians are
        // runnable objects)
        this.myShipThread = new Thread(myShip);
        this.yourShipThread = new Thread(yourShip);

        this.handleKeyPressEvent();
    }

    // Starts the race by initializing the start time and starting the threads for
    // all participants
    public void startRace() {
        this.myShipThread.start(); // Start myShip thread
        this.yourShipThread.start(); // Start yourShip thread
    }

    private void handleKeyPressEvent() {
        scene.setOnKeyPressed(e -> {
            activeKeys.add(e.getCode());
            System.out.println("Key pressed: " + e.getCode());
            System.out.println("Active Keys: " + activeKeys); // Print active keys set
        });

        scene.setOnKeyReleased(e -> {
            activeKeys.remove(e.getCode());
            System.out.println("Key released: " + e.getCode());
            System.out.println("Active Keys: " + activeKeys); // Print active keys set
        });
    }

    private void moveSprite(Ship ship, KeyCode forward, KeyCode left, KeyCode right) {
        if (activeKeys.contains(forward)) {
            ship.forward();
        }
        if (activeKeys.contains(right)) {
            ship.rotateRight();
        }
        if (activeKeys.contains(left)) {
            ship.rotateLeft();
        }
    }

    private void shoot(Ship yourShip, Ship enemyShip, KeyCode space) {
        long currentTime = System.nanoTime(); // Current time in nanoseconds
        long cooldownPeriod = 100_000_000L; // 2 seconds in nanoseconds
        long reloadPeriod = 3_000_000_000L;

        List<Projectile> projectiles = yourShip.getBullets();
        int bulletsLeft = yourShip.bulletsLeft;
        long lastShootTime = yourShip.lastShot;

        if (bulletsLeft == 0 && currentTime - lastShootTime >= reloadPeriod) {
            yourShip.bulletsLeft = 5;
        }

        if (bulletsLeft == 0) {
            long reloadTimeLeft = ((reloadPeriod - (currentTime - lastShootTime)) / 1_000_000_000L) + 1;
            gc.strokeText("Reloading in " + reloadTimeLeft, "yourShip".equals(yourShip.getShipName()) ? 50 : 450, 450);
        }

        if (activeKeys.contains(space) && (currentTime - lastShootTime >= cooldownPeriod) && bulletsLeft != 0) {
            yourShip.shoot();
            yourShip.bulletsLeft--;
            yourShip.lastShot = currentTime;
        }

        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();

            projectile.render(gc);

            if (projectile.hitbox.intersects(enemyShip.hitbox)) {
                System.out.println("Hit" + yourShip.getShipName() + "by a" + projectile.getName());
                enemyShip.minusHealth(5);
                projectile.stop();
            }

            if (!projectile.isPlaying()) {
                iterator.remove();
            }
        }
    }

    // Main game loop method, called by AnimationTimer every frame
    @Override
    public void handle(long currentNanoTime) {
        // Clear the canvas for the new frame
        gc.clearRect(0, 0, GameProper.WINDOW_WIDTH, GameProper.WINDOW_HEIGHT);

        gc.strokeRect(5, 0, 100, 32);
        gc.setFill(Color.RED);
        gc.fillRect(5, 1, myShip.getHealth(), 30);
        moveSprite(myShip, KeyCode.UP, KeyCode.LEFT, KeyCode.RIGHT);
        shoot(myShip, yourShip, KeyCode.ENTER);

        gc.strokeRect(GameProper.WINDOW_WIDTH - 100, 0, 100, 32);
        gc.setFill(Color.RED);
        gc.fillRect(GameProper.WINDOW_WIDTH - 100 + (100 - yourShip.getHealth()), 1, yourShip.getHealth(), 30);
        moveSprite(yourShip, KeyCode.W, KeyCode.A, KeyCode.D);
        shoot(yourShip, myShip, KeyCode.SPACE);

        this.myShip.render(gc); // Render myShip
        this.yourShip.render(gc); // Render yourShip
    }
}
