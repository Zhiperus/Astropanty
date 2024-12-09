package org.astropanty.ui.game.logic;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.astropanty.ui.game.entities.Projectile;
import org.astropanty.ui.game.entities.Ship;
import org.astropanty.ui.game.entities.Wall;
import org.astropanty.ui.game.screens.GameProper;
import org.astropanty.ui.game.screens.WinningScreen;
import org.astropanty.ui.navigation.ScreenController;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
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
    private final List<Wall> walls; // list of wall entities for selected map
    private final Set<KeyCode> activeKeys = new HashSet<>(); // Tracks currently pressed keys
    private final long startTime;

    private final ScreenController screenController;
    private final Runnable navigateToMenu;

    /**
     * Constructor for initializing the GameTimer with necessary dependencies.
     */
    public GameTimer(GraphicsContext gc, Scene scene, Ship player1Ship, Ship player2Ship, List<Wall> walls, Runnable navigateToMenu, ScreenController screenController) {
        this.startTime = System.nanoTime(); // Store the start time in nanoseconds
        this.gc = gc;
        this.scene = scene;
        this.player1Ship = player1Ship;
        this.player2Ship = player2Ship;
        this.walls = walls;

        // Threads for concurrent ship logic (e.g., AI or complex calculations)
        this.player1Thread = new Thread(player1Ship);
        this.player2Thread = new Thread(player2Ship);

        // Initialize key press and release event handlers
        this.setupKeyHandlers();

        this.screenController = screenController;
        this.navigateToMenu = navigateToMenu;
    }

    /**
     * Check for collisions between ships and walls.
     *
     * @param ship The ship to check.
     */
    private void checkShipCollision(Ship ship) {
        for (Wall wall : walls) {
            Rectangle2D shipBounds = new Rectangle2D(ship.getXPos(), ship.getYPos(), ship.getWidth(), ship.getHeight());
            if (wall.checkCollision(shipBounds)) {
                System.out.println(ship.getShipName() + " collided with " + wall.getName());
                adjustShipPosition(ship, wall);
            }
        }
    }

    /**
     * Adjusts the ship's position to prevent it from moving into the wall.
     *
     * @param ship The ship to adjust.
     * @param wall The wall it collided with.
     */
    private void adjustShipPosition(Ship ship, Wall wall) {
        Rectangle2D shipBounds = new Rectangle2D(ship.getXPos(), ship.getYPos(), ship.getWidth(), ship.getHeight());
        Rectangle2D wallBounds = wall.getBounds();
        if (shipBounds.intersects(wallBounds)) {
            if (shipBounds.getMinX() < wallBounds.getMinX()) {
                ship.setXPos(wallBounds.getMinX() - ship.getWidth());
            } else if (shipBounds.getMaxX() > wallBounds.getMaxX()) {
                ship.setXPos(wallBounds.getMaxX());
            }
            
            if (shipBounds.getMinY() < wallBounds.getMinY()) {
                ship.setYPos(wallBounds.getMinY() - ship.getHeight());
            } else if (shipBounds.getMaxY() > wallBounds.getMaxY()) {
                ship.setYPos(wallBounds.getMaxY());
            }
        }
    }

    /**
     * Check for collisions between projectiles and walls.
     */
    private void checkProjectileCollsions(Ship shooter){
        Iterator<Projectile> iterator = shooter.getBullets().iterator();
        while (iterator.hasNext()){
            Projectile projectile = iterator.next();
            for (Wall wall: walls){
                if (wall.checkCollision(projectile.hitbox)){
                    System.out.println("Projectile hit " + wall.getName());
                    projectile.stop();
                    iterator.remove();
                    break;
                }
            }
        }
    }

    /**
     * Render all walls.
     */
    private void renderWalls(){
        for (Wall wall : walls){
            wall.render(gc);
        }
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

    // Checks if a winner should be declared (if any kart has finished or time exceeds 120 seconds)
    public void checkWinner() {
        if (!this.player1Thread.isAlive() || !this.player2Thread.isAlive() || (System.nanoTime() - this.startTime) / 1_000_000_000 > 119) { // If race time exceeds 120 seconds
            this.stop();           // Stop the AnimationTimer (game loop)
            player1Ship.stop();         // Stop the karts and pedestrians
            player2Ship.stop();

            // Determine the winner based on remaining lives
            String winner;
            if (player1Ship.getHealth() > player2Ship.getHealth()) {
                gc.setFill(Color.RED); // Set color for myKart winner
                winner = player1Ship.getShipName();
            }else if (player2Ship.getHealth() > player1Ship.getHealth()) {
                gc.setFill(Color.RED); // Set color for myKart winner
                winner = player2Ship.getShipName();
            }else {
            	gc.setFill(Color.BLUE); // Set color for yourKart winner
                winner = "Draw!";
            }

            // Interrupt all threads (stop them immediately)
            player1Thread.interrupt();
            player2Thread.interrupt();

            Platform.runLater(() -> {
                try {
                    Thread.sleep(2000); // Delay for displaying winner
                    if(!winner.equals("Draw!"))
                        screenController.navigate(new WinningScreen(navigateToMenu, winner, player1Ship.getShipName() == winner ? player1Ship.getImage() : player2Ship.getImage(), null));
                    else
                        screenController.navigate(new WinningScreen(navigateToMenu, winner, player1Ship.getImage(), player2Ship.getImage()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
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
        gc.setFont(Font.font("Orbitron", 20));
        long currentSecond = (System.nanoTime() - this.startTime) / 1_000_000_000; // Calculate elapsed seconds

        // Clear the canvas for rendering
        gc.clearRect(0, 0, GameProper.WINDOW_WIDTH, GameProper.WINDOW_HEIGHT);

        // Render walls
        renderWalls();

        // Handle ship collision with walls
        checkShipCollision(player1Ship);
        checkShipCollision(player2Ship);

        // Handle projectile collision with walls
        checkProjectileCollsions(player1Ship);
        checkProjectileCollsions(player2Ship);

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
        gc.strokeText(currentSecond / 60 + " : " + ((currentSecond % 59 < 10) ? "0" : "") + currentSecond % 59, GameProper.WINDOW_WIDTH / 2, 20); // Time display

        checkWinner();
    }
}
