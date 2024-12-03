package org.astropanty.game.logic;

import javafx.animation.AnimationTimer; 
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext; 
import javafx.scene.image.Image;       
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;        
import java.util.Iterator;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import org.astropanty.game.entities.Projectile;
import org.astropanty.game.entities.Ship;
import org.astropanty.game.screens.GameProper;

public class GameTimer extends AnimationTimer {
    private GraphicsContext gc;            
    private Scene scene;
    private Ship myShip, yourShip;         
    private Thread myShipThread, yourShipThread;
    private Set<KeyCode> activeKeys = new HashSet<>();

    public GameTimer(GraphicsContext gc, Scene scene, GameProper game) {
        this.gc = gc; 
        this.scene = scene;

        this.myShip = new Ship(10, 300, "myShip", new Image(getClass().getResource("/org/astropanty/yourShip.png").toExternalForm(), 40, 40, false, false));
        this.yourShip = new Ship(490, 300, "yourShip", new Image(getClass().getResource("/org/astropanty/myShip.png").toExternalForm(), 40, 40, false, false));

        this.myShipThread = new Thread(myShip);
        this.yourShipThread = new Thread(yourShip);
  
        this.handleKeyPressEvent();
    }

    public void startRace() {
        this.myShipThread.start();         
        this.yourShipThread.start();      
    }

    private void handleKeyPressEvent() {
        scene.setOnKeyPressed(e -> {
            activeKeys.add(e.getCode());
            System.out.println("Key pressed: " + e.getCode());
            System.out.println("Active Keys: " + activeKeys);  // Print active keys set
        });
        
        scene.setOnKeyReleased(e -> {
            activeKeys.remove(e.getCode());
            System.out.println("Key released: " + e.getCode());
            System.out.println("Active Keys: " + activeKeys);  // Print active keys set
        });
    }
    
    private void moveSprite(Ship ship, KeyCode forward, KeyCode left, KeyCode right) {
        if (activeKeys.contains(forward)) {
           ship.forward(); 
        }
        if (activeKeys.contains(right)){
    	 	ship.rotateRight();
    	}
        if(activeKeys.contains(left)) {
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
        
        if(bulletsLeft == 0 && currentTime - lastShootTime >= reloadPeriod) {
        	yourShip.bulletsLeft = 5;
        }
        
        if(bulletsLeft == 0) {
        	long reloadTimeLeft = ((reloadPeriod - ( currentTime - lastShootTime ))  / 1_000_000_000L) + 1 ;
        	gc.strokeText("Reloading in " + reloadTimeLeft, yourShip.getShipName() == "yourShip" ? 50 : 450 , 450);
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
            
            if(projectile.hitbox.intersects(enemyShip.hitbox)) {
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
        
        gc.strokeRect(5, 0, 100, 32);
        gc.setFill(Color.RED);
        gc.fillRect(5, 1, myShip.getHealth(), 30);
        moveSprite(myShip, KeyCode.UP, KeyCode.LEFT, KeyCode.RIGHT);
        shoot(myShip, yourShip, KeyCode.ENTER);
        
        gc.strokeRect(GameProper.WINDOW_WIDTH - 100, 0, 100, 32);
        gc.setFill(Color.RED);
        gc.fillRect(GameProper.WINDOW_WIDTH - 100 + (100 - yourShip.getHealth()), 1, yourShip.getHealth(), 30);
        moveSprite(yourShip, KeyCode.W,  KeyCode.A, KeyCode.D);
        shoot(yourShip, myShip, KeyCode.SPACE);
 
        this.myShip.render(gc); 
        this.yourShip.render(gc); 
    }
}
