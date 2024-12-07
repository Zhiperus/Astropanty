package org.astropanty.ui.game.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

/**
 * Represents wall entities in the game.
 * Wall entities exist in which they implement collision events to either block Ship movement or Projectiles.
 */
public class Walls extends Sprite {
    private final String wallName;       // Name of the wall entity (e.g., for identification)
    public Rectangle2D hitbox;           // Represents the projectile's hitbox for collision detection

    /**
     * Constructor to initialize the projectile with its attributes.
     * 
     * @param name             The name of the projectile
     * @param x                The initial x-coordinate
     * @param y                The initial y-coordinate
     * @param rotation         The initial rotation angle (in degrees)
     * @param PROJECTILE_IMAGE The image representing the projectile
     */
    public Walls(String name, double x, double y, Image PROJECTILE_IMAGE) {
        super(x, y, PROJECTILE_IMAGE); // Initialize the base Sprite class
        this.wallName = name;
        this.hitbox = new Rectangle2D(this.xPos, this.yPos, this.width, this.height); // Initialize the hitbox
    }

    /**
     * Returns the name of the projectile.
     * 
     * @return The projectile's name
     */
    public String getName() {
        return this.wallName;
    }

}
