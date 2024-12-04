package org.astropanty.ui.game.logic;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.astropanty.ui.game.entities.Projectile;
import org.astropanty.ui.game.entities.Ship;
import org.astropanty.ui.game.screens.GameProper;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameTimer extends AnimationTimer {
    private final GraphicsContext gc;
    private final Scene scene;
    private final Ship player1Ship;
    private final Ship player2Ship;
    private final Thread player1Thread;
    private final Thread player2Thread;
    private final Set<KeyCode> activeKeys = new HashSet<>();

    public GameTimer(GraphicsContext gc, Scene scene, Ship player1Ship, Ship player2Ship) {
        this.gc = gc;
        this.scene = scene;

        this.player1Ship = player1Ship;
        this.player2Ship = player2Ship;

        this.player1Thread = new Thread(player1Ship);
        this.player2Thread = new Thread(player2Ship);

        this.handleKeyPressEvent();
    }

    public void startRace() {
        this.player1Thread.start();
        this.player2Thread.start();
    }

    private void handleKeyPressEvent() {
        scene.setOnKeyPressed(e -> {
            activeKeys.add(e.getCode());
            System.out.println("Key pressed: " + e.getCode());
            System.out.println("Active Keys: " + activeKeys);
        });

        scene.setOnKeyReleased(e -> {
            activeKeys.remove(e.getCode());
            System.out.println("Key released: " + e.getCode());
            System.out.println("Active Keys: " + activeKeys);
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
        long currentTime = System.nanoTime();
        long cooldownPeriod = 100_000_000L;
        long reloadPeriod = 3_000_000_000L;

        List<Projectile> projectiles = yourShip.getBullets();
        int bulletsLeft = yourShip.bulletsLeft;
        long lastShootTime = yourShip.lastShot;

        if (bulletsLeft == 0 && currentTime - lastShootTime >= reloadPeriod) {
            yourShip.bulletsLeft = 5;
        }

        if (bulletsLeft == 0) {
            long reloadTimeLeft = ((reloadPeriod - (currentTime - lastShootTime)) / 1_000_000_000L) + 1;
            gc.setFont(Font.font("Orbitron", 20));
            gc.setStroke(Color.WHITE);
            gc.strokeText("Reloading in " + reloadTimeLeft,
                    "yourShip".equals(yourShip.getShipName()) ? GameProper.WINDOW_WIDTH - 180 : 50, 450);
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

    @Override
    public void handle(long currentNanoTime) {
        gc.clearRect(0, 0, GameProper.WINDOW_WIDTH, GameProper.WINDOW_HEIGHT);

        moveSprite(player1Ship, KeyCode.W, KeyCode.A, KeyCode.D);
        shoot(player1Ship, player2Ship, KeyCode.SPACE);

        moveSprite(player2Ship, KeyCode.UP, KeyCode.LEFT, KeyCode.RIGHT);
        shoot(player2Ship, player1Ship, KeyCode.ENTER);

        this.player1Ship.render(gc);
        this.player2Ship.render(gc);

        gc.setStroke(Color.WHITE);
        gc.strokeRect(20, 0, 100, 32);
        gc.setFill(Color.RED);
        gc.fillRect(20, 1, player1Ship.getHealth(), 30);

        gc.setStroke(Color.WHITE);
        gc.strokeRect(GameProper.WINDOW_WIDTH - 120, 0, 100, 32);
        gc.setFill(Color.RED);
        gc.fillRect(GameProper.WINDOW_WIDTH - 120 + (100 - player2Ship.getHealth()), 1, player2Ship.getHealth(), 30);
    }
}
