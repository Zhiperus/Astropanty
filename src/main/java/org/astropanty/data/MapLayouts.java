package org.astropanty.data;

import javafx.scene.image.Image;
import org.astropanty.ui.game.entities.Wall;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides predefined wall layouts for different maps.
 */
public class MapLayouts {
    private final static Image LONG_WALL_IMG = new Image(
        MapLayouts.class.getResource("/org/astropanty/longWall.png").toExternalForm(),false);
    private final static Image CUBE_WALL_IMG = new Image(
        MapLayouts.class.getResource("/org/astropanty/cubeWall.png").toExternalForm(),false);
    private final static Image RECT_WALL_IMG = new Image(
        MapLayouts.class.getResource("/org/astropanty/rectWall.png").toExternalForm(),false);

    /**
     * Returns wall entities for Map 1 (just need a name for the map).
     */
    public static List<Wall> getMap1Wall(){
        List<Wall> walls = new ArrayList<>();
       
        walls.add(new Wall("longWall1", 100, 300, LONG_WALL_IMG));
        walls.add(new Wall("cubeWall1", 400, 300, CUBE_WALL_IMG));

        return walls;
    }

    /**
     * Returns wall entities for Map 2 (just need a name for the map).
     */
    public static List<Wall> getMap2Walls(){
        List<Wall> walls = new ArrayList<>();
       
        walls.add(new Wall("longWall2", 300, 300, LONG_WALL_IMG));
        walls.add(new Wall("cubeWall2", 500, 700, CUBE_WALL_IMG));
        walls.add(new Wall("rectWall2", 500, 700, RECT_WALL_IMG));

        return walls;
    }
}
