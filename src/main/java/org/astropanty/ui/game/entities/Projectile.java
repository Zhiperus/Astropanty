package org.astropanty.ui.game.entities;

import org.astropanty.ui.game.screens.GameProper;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

/**
 * Represents a projectile in the game.
 * A projectile moves in a specified direction and checks for off-screen or collision events.
 */
public class Projectile extends Sprite implements Runnable {
    private final String projectileName; // Name of the projectile (e.g., for identification)
    private boolean playing;             // Indicates if the projectile is still active
    public Rectangle2D hitbox;           // Represents the projectile's hitbox for collision detection
    private int projectileSpeed; // Speed at which the projectile moves

    /**
     * Constructor to initialize the projectile with its attributes.
     * 
     * @param name             The name of the projectile
     * @param x                The initial x-coordinate
     * @param y                The initial y-coordinate
     * @param rotation         The initial rotation angle (in degrees)
     * @param PROJECTILE_IMAGE The image representing the projectile
     */
    public Projectile(String name, double x, double y, double rotation, int projectileSpeed, Image PROJECTILE_IMAGE) {
        super(x, y, PROJECTILE_IMAGE); // Initialize the base Sprite class
        this.projectileName = name;
        this.playing = true;          // Mark the projectile as active initially
        this.rotation = rotation;    // Set the initial rotation
        this.projectileSpeed = projectileSpeed;
        this.hitbox = new Rectangle2D(this.xPos, this.yPos, this.width, this.height); // Initialize the hitbox
    }

    /**
     * Returns the name of the projectile.
     * 
     * @return The projectile's name
     */
    public String getName() {
        return this.projectileName;
    }

    /**
     * Marks the projectile as inactive, stopping its movement and logic.
     */
    public void stop() {
        this.playing = false;
    }

    /**
     * Checks if the projectile is still active.
     * 
     * @return true if active, false otherwise
     */
    public boolean isPlaying() {
        return this.playing;
    }

    /**
     * Determines if the projectile has moved off the screen.
     * 
     * @return true if the projectile is off-screen, false otherwise
     */
    private boolean isOffScreen() {
        return this.xPos < 0 || this.xPos > GameProper.WINDOW_WIDTH ||
               this.yPos < 0 || this.yPos > GameProper.WINDOW_HEIGHT;
    }

    /**
     * Moves the projectile in the direction of its rotation.
     * The movement is based on trigonometric calculations for the x and y axes.
     */
    public void move() {
        this.setXPos(this.getXPos() + Math.sin(Math.toRadians(this.getRotation())) * projectileSpeed);
        this.setYPos(this.getYPos() - Math.cos(Math.toRadians(this.getRotation())) * projectileSpeed);
    }

    /**
     * Starts the projectile's movement logic in a separate thread.
     */
    @Override
    public void run() {
        this.race(); // Execute the race logic for movement
    }

    /**
     * Handles the continuous movement of the projectile.
     * The projectile updates its position and hitbox on each iteration.
     * It stops if it moves off the screen or is explicitly stopped.
     */
    public void race() {
        while (playing) {
            // Move the projectile
            move();

            // Update the hitbox position to reflect the current coordinates
            this.hitbox = new Rectangle2D(this.xPos, this.yPos, this.width, this.height);

            // Stop the projectile if it moves off-screen
            if (isOffScreen()) {
                stop();
            }

            // Pause the thread to control the projectile's frame rate
            try {
                Thread.sleep(16); // ~60 frames per second
            } catch (InterruptedException e) {
                // Stop the projectile if the thread is interrupted
                stop();
                break;
            }
        }
    }
}
