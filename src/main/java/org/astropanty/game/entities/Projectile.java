package org.astropanty.game.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import org.astropanty.game.screens.GameProper;

public class Projectile extends Sprite implements Runnable{
	private String projectileName;
	private boolean playing;      
	public Rectangle2D hitbox;
	
	private final int PROJECTILE_SPEED = 8;

	public Projectile(String name, double x, double y, double rotation, Image BULLET_IMAGE) {
		super(x, y, BULLET_IMAGE);
		this.projectileName = name;
		this.playing = true;      
		this.rotation = rotation;
		this.hitbox = new Rectangle2D(this.xPos, this.yPos, this.width, this.height);
	}
	
	public String getName() {
		return this.projectileName;
	}

	public void stop() {
		this.playing = false;
	}
	
	public void run() {
		this.race();
	}

	public boolean isPlaying() {
		return this.playing;
	}
	
	private boolean isOffScreen() {
	    return this.xPos < 0 || this.xPos > GameProper.WINDOW_WIDTH ||
	           this.yPos < 0 || this.yPos > GameProper.WINDOW_HEIGHT;
	}

	public void race() {
		while (playing) {
	        move();
	        this.hitbox = new Rectangle2D(this.xPos, this.yPos, this.width, this.height);	

	        if (isOffScreen()) {
	            stop(); 
	        }

	        try {
	            Thread.sleep(16); 
	        } catch (InterruptedException e) {
	            stop(); 
	            break;
	        }
	    }
	}

	public void move() {
		this.setXPos(this.getXPos() + Math.sin(Math.toRadians(this.getRotation())) * PROJECTILE_SPEED);
        this.setYPos(this.getYPos() - Math.cos(Math.toRadians(this.getRotation())) * PROJECTILE_SPEED);  
	}
}

