package org.astropanty.ui.game.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

/**
 * Represents wall entities in the game.
 * Wall entities block movement for ships and projectiles via collision detection.
 */
public class Wall extends Sprite {
    private final String wallName; // Name of the wall entity
    public Rectangle2D hitbox;     // Represents the wall's hitbox for collision detection

    /**
     * Constructor to initialize a wall entity.
     *
     * @param name       The name of the wall entity
     * @param x          The x-coordinate position
     * @param y          The y-coordinate position
     * @param wallImage  The image representing the wall
     */
    public Wall(String name, double x, double y, Image wallImage) {
        super(x, y, wallImage); // Call the Sprite class constructor
        this.wallName = name;
        updateHitbox(); // Initialize the hitbox
    }

    /**
     * Updates the hitbox based on the wall's current position and size.
     */
    public void updateHitbox() {
        this.hitbox = new Rectangle2D(this.xPos, this.yPos, this.width, this.height);
    }

    /**
     * Detects collision with another entity's hitbox.
     *
     * @param entityHitbox The hitbox of the entity to check collision against.
     * @return True if a collision occurs; false otherwise.
     */
    public boolean checkCollision(Rectangle2D entityHitbox) {
        return this.hitbox.intersects(entityHitbox);
    }

    /**
     * Renders the wall and updates its hitbox.
     *
     * @param gc The GraphicsContext for rendering.
     */
    @Override
    public void render(javafx.scene.canvas.GraphicsContext gc) {
        super.render(gc); // Render the wall image
        updateHitbox();   // Update the hitbox to align with the wall's position
    }

    /** 
     * Returns the bounds of the wall entity. 
     * 
     * @return The bounds of the wall as a Rectangle2D. 
     */
    public Rectangle2D getBounds(){
        return this.hitbox;
    }

    /**
     * Returns the name of the wall entity.
     *
     * @return The wall's name.
     */
    public String getName() {
        return this.wallName;
    }
}
