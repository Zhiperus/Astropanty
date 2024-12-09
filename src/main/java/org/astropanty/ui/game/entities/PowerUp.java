package org.astropanty.ui.game.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public class PowerUp extends Sprite{

    public enum PowerUpType {
        SPEED,
        DAMAGE,
        HEALTH
    }

    private final PowerUpType type;
    private final double x;
    private final double y;
    private double remainingTime;
    private boolean active;
    private Rectangle2D hitbox; // Ship's hitbox for collision detection

    public PowerUp(PowerUpType type, double x, double y, Image icon){
        super(x, y, icon);
        this.type = type;
        this.x = x;
        this.y = y;
        this.active = true;
        this.remainingTime = 5; // Set the initial duration
        this.hitbox = new Rectangle2D(x, y, this.width, this.height); 
    }

    // Getters
    public PowerUpType getType() {
        return type;
    }

    public Rectangle2D getHitbox(){
        return this.hitbox;
    }

    public void update(double deltaTime) {
        if (active) {
            remainingTime -= deltaTime; // Decrease remaining time based on the frame's delta time
            if (remainingTime <= 0) {
                active = false; // Deactivate power-up when the time is over
            }
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
    }

    // Apply the power-up effect to the ship
    public void applyTo(Ship ship) {
        if (!active) return;

        switch (type) {
            case SPEED:
                ship.increaseSpeed(5);; // Example: Temporarily increase speed
                break;
            case DAMAGE:
                ship.increaseDamage(10); // Example: Temporarily increase damage
                break;
            case HEALTH:
                ship.increaseHealth(20); // Example: Restore health points
                break;
        }

        // Deactivate power-up after application
        deactivate();
    }
}
