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

/**
 * GameTimer class is responsible for managing the game loop.
 * It handles animations, player controls, and in-game events like shooting and collisions.
 */
public class GameTimer extends AnimationTimer {
    private final GraphicsContext gc; // Graphics context for rendering game objects
    private final Scene scene;       // The game scene
    private final Ship player1Ship;  // Player 1's ship
    private final Ship player2Ship;  // Player 2's ship
    private final Thread player1Thread; // Thread for player 1's ship logic
    private final Thread player2Thread; // Thread for player 2's ship logic
    private final Set<KeyCode> activeKeys = new HashSet<>(); // Tracks currently pressed keys

    /**
     * Constructor for initializing the GameTimer with necessary dependencies.
     */
    public GameTimer(GraphicsContext gc, Scene scene, Ship player1Ship, Ship player2Ship) {
        this.gc = gc;
        this.scene = scene;
        this.player1Ship = player1Ship;
        this.player2Ship = player2Ship;

        // Threads for concurrent ship logic (e.g., AI or complex calculations)
        this.player1Thread = new Thread(player1Ship);
        this.player2Thread = new Thread(player2Ship);

        // Initialize key press and release event handlers
        this.setupKeyHandlers();
    }

    /**
     * Starts the race by launching threads for player ship logic.
     */
    public void startRace() {
        this.player1Thread.start();
        this.player2Thread.start();
    }

    /**
     * Configures key press and key release event listeners.
     */
    private void setupKeyHandlers() {
        scene.setOnKeyPressed(e -> activeKeys.add(e.getCode())); // Track pressed keys
        scene.setOnKeyReleased(e -> activeKeys.remove(e.getCode())); // Remove released keys
    }

    /**
     * Handles ship movement based on specified controls.
     */
    private void moveShip(Ship ship, KeyCode forward, KeyCode left, KeyCode right) {
        if (activeKeys.contains(forward)) {
            ship.forward(); // Move forward
        }
        if (activeKeys.contains(right)) {
            ship.rotateRight(); // Rotate right
        }
        if (activeKeys.contains(left)) {
            ship.rotateLeft(); // Rotate left
        }
    }

    /**
     * Handles shooting logic for a ship, including cooldowns and collisions.
     */
    private void handleShooting(Ship shooter, Ship target, KeyCode shootKey) {
        long currentTime = System.nanoTime();
        final long COOLDOWN_PERIOD = 100_000_000L;  // Cooldown between shots (100ms)
        final long RELOAD_PERIOD = 3_000_000_000L; // Reload time (3 seconds)

        List<Projectile> projectiles = shooter.getBullets();
        int bulletsLeft = shooter.bulletsLeft;
        long lastShootTime = shooter.lastShot;

        // Handle reload logic
        if (bulletsLeft == 0 && currentTime - lastShootTime >= RELOAD_PERIOD) {
            shooter.bulletsLeft = 5; // Reset bullet count after reload
        }

        // Display reload countdown
        if (bulletsLeft == 0) {
            long reloadTimeLeft = ((RELOAD_PERIOD - (currentTime - lastShootTime)) / 1_000_000_000L) + 1;
            gc.setFont(Font.font("Orbitron", 20));
            gc.setStroke(Color.WHITE);
            gc.strokeText(
                "Reloading in " + reloadTimeLeft,
                "Player 1".equals(shooter.getShipName()) ? 50 :  GameProper.WINDOW_WIDTH - 180,
                450
            );
        }

        // Handle shooting logic
        if (activeKeys.contains(shootKey) && currentTime - lastShootTime >= COOLDOWN_PERIOD && bulletsLeft > 0) {
            shooter.shoot(); // Fire a projectile
            shooter.bulletsLeft--; // Decrement bullet count
            shooter.lastShot = currentTime; // Update last shot time
        }

        // Render and check for projectile collisions
        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.render(gc);

            // Check for collision with the target ship
            if (projectile.hitbox.intersects(target.hitbox)) {
                System.out.println("Hit " + target.getShipName() + " by " + projectile.getName());
                target.minusHealth(5); // Reduce target's health
                projectile.stop(); // Stop the projectile
            }

            // Remove projectile if it is no longer active
            if (!projectile.isPlaying()) {
                iterator.remove();
            }
        }
    }

     /**
     * Renders a health bar for a given ship.
     */
    private void renderHealthBar(Ship ship, double x, double y, double width) {
        gc.setStroke(Color.WHITE);
        gc.strokeRect(x, y, width, 32);
        gc.setFill(Color.RED);
        gc.fillRect(x, y + 1, ship.getHealth(), 30);
    }

    /**
     * The main game loop, called on every frame.
     */
    @Override
    public void handle(long currentNanoTime) {
        // Clear the canvas for rendering
        gc.clearRect(0, 0, GameProper.WINDOW_WIDTH, GameProper.WINDOW_HEIGHT);

        // Handle Player 1 controls
        moveShip(player1Ship, KeyCode.W, KeyCode.A, KeyCode.D);
        handleShooting(player1Ship, player2Ship, KeyCode.SPACE);

        // Handle Player 2 controls
        moveShip(player2Ship, KeyCode.UP, KeyCode.LEFT, KeyCode.RIGHT);
        handleShooting(player2Ship, player1Ship, KeyCode.ENTER);

        // Render ships
        player1Ship.render(gc);
        player2Ship.render(gc);

        // Render health bars
        renderHealthBar(player1Ship, 20, 0, 100);
        renderHealthBar(player2Ship, GameProper.WINDOW_WIDTH - 120, 0, 100);
    }
}
