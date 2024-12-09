package org.astropanty.ui.game.logic;

import org.astropanty.ui.game.entities.PowerUp;

public class ActivePowerUp {
    private final PowerUp.PowerUpType type;
    private double remainingTime;

    public ActivePowerUp(PowerUp.PowerUpType type, double duration) {
        this.type = type;
        this.remainingTime = duration;
    }

    public PowerUp.PowerUpType getType() {
        return type;
    }

    public double getRemainingTime() {
        return remainingTime;
    }

    public void reduceTime(double deltaTime) {
        this.remainingTime -= deltaTime;
    }
}
