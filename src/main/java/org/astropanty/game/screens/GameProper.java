package org.astropanty.game.screens;

import org.astropanty.game.logic.GameTimer;
import org.astropanty.navigation.Screen;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameProper implements Screen{
    private Scene scene;
    private Group root;
	private Canvas canvas;

    public final static int WINDOW_WIDTH = 568;
	public final static int WINDOW_HEIGHT = 568;

    @Override
    public Scene content() {
        this.root = new Group();
		this.scene = new Scene( root );
		this.canvas = new Canvas( GameProper.WINDOW_WIDTH, GameProper.WINDOW_HEIGHT );
        this.root.getChildren().add( this.canvas );

        GraphicsContext gc = this.canvas.getGraphicsContext2D();

        GameTimer gameTimer = new GameTimer(gc, scene, this);
        gameTimer.start();		
        gameTimer.startRace();
        
        return this.scene;
    }

}
