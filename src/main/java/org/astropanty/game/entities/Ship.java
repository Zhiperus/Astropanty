package org.astropanty.game.entities;

import java.util.ArrayList;
import java.util.List;

import org.astropanty.game.screens.GameProper;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public class Ship extends Sprite implements Runnable {
	private final String name;
	private boolean playing;
	private int health;
	private final List<Projectile> projectiles;
	private final Image bulletImage;

	public int bulletsLeft;
	public long lastShot;
	public Rectangle2D hitbox;

	private final int MOVEMENT_SPEED = 3;
	private final int ROTATION_SPEED = 3;

	public Ship(int x, int y, String name, Image SHIP_IMAGE) {
		super(x, y, SHIP_IMAGE);
		this.playing = true;
		this.name = name;
		this.health = 100;
		this.projectiles = new ArrayList<>();
		this.bulletImage = new Image(getClass().getResource("/org/astropanty/bullet.png").toExternalForm(), 10, 10,
				false, false);
		this.bulletsLeft = 5;
		this.lastShot = 0;
		this.hitbox = new Rectangle2D(this.xPos, this.yPos, this.width, this.height);
	}

	public void shoot() {
		Projectile bullet = new Projectile(
				"Bullet",
				this.xPos + 13,
				this.yPos + 13,
				this.rotation,
				this.bulletImage);
		projectiles.add(bullet);

		Thread bulletThread = new Thread(bullet);
		bulletThread.start();
	}

	public List<Projectile> getBullets() {
		return projectiles;
	}

	public String getShipName() {
		return this.name;
	}

	public int getHealth() {
		return this.health;
	}

	public void minusHealth(int damage) {
		this.health -= damage;
	}

	public void stop() {
		this.playing = false;
	}

	public void rotateRight() {
		this.rotation = (this.rotation + ROTATION_SPEED) % 360;
	}

	public void rotateLeft() {
		this.rotation = (this.rotation - ROTATION_SPEED + 360) % 360;
	}

	public void forward() {
		this.setXPos(this.getXPos() + Math.sin(Math.toRadians(this.getRotation())) * MOVEMENT_SPEED);
		this.setYPos(this.getYPos() - Math.cos(Math.toRadians(this.getRotation())) * MOVEMENT_SPEED);
	}

	@Override
	public void run() {
		this.race();
	}

	private void race() {
		while (playing) {
			this.hitbox = new Rectangle2D(this.xPos, this.yPos, this.width, this.height);

			if (this.xPos > GameProper.WINDOW_WIDTH)
				this.setXPos(0);
			else if (this.xPos < -50)
				this.setXPos(GameProper.WINDOW_WIDTH);
			else if (this.yPos > GameProper.WINDOW_HEIGHT)
				this.setYPos(0);
			else if (this.yPos < -50)
				this.setYPos(GameProper.WINDOW_HEIGHT);
		}
	}

	/**********************************************************************************************
	 * Generates a random value to move the kart forward by a small step.
	 ***********************************************************************************************/
}
