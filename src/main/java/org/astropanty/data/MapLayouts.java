package org.astropanty.data;

import javafx.scene.image.Image;
import org.astropanty.ui.game.entities.Wall;
import org.astropanty.ui.game.screens.GameProper;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides predefined wall layouts for different maps.
 */
public class MapLayouts {
    private final static Image CUBE_WALL_IMG = new Image(
        MapLayouts.class.getResource("/org/astropanty/cubeWall.png").toExternalForm(),false);
    private final static Image LONG_WALL_IMG = new Image(
        MapLayouts.class.getResource("/org/astropanty/longWall.png").toExternalForm(),false);
        private final static Image LONG_WALL_HZ_IMG = new Image(
        MapLayouts.class.getResource("/org/astropanty/longWallHorizontal.png").toExternalForm(),false);
    
    private final static Image RECT_WALL_IMG = new Image(
        MapLayouts.class.getResource("/org/astropanty/rectWall.png").toExternalForm(),false);
    private final static Image RECT_WALL_HZ_IMG = new Image(
        MapLayouts.class.getResource("/org/astropanty/rectWallHorizontal.png").toExternalForm(),false);

    /**
     * Returns wall entities for Map 1 (just need a name for the map).
     */
    public static List<Wall> getMap1Wall(){
        List<Wall> walls = new ArrayList<>();
        int windowWidth = GameProper.WINDOW_WIDTH;
        int windowHeight = GameProper.WINDOW_HEIGHT;

        int lWallWidth = (int) LONG_WALL_IMG.getWidth();
        int lWallHeight = (int) LONG_WALL_IMG.getHeight();

        int lWallHZWidth = (int) LONG_WALL_HZ_IMG.getWidth();
        int lWallHZHeight = (int) LONG_WALL_HZ_IMG.getHeight();

        // border walls left side
        walls.add(new Wall("borderUpperLeft", 7 - LONG_WALL_IMG.getWidth(), -8, LONG_WALL_IMG));
        walls.add(new Wall("borderUpperLeftHZ", 60 - LONG_WALL_IMG.getWidth(), -53, LONG_WALL_HZ_IMG));
        walls.add(new Wall("borderLowerLeft", 7 - LONG_WALL_IMG.getWidth(), windowHeight - 180, LONG_WALL_IMG));
        walls.add(new Wall("borderLowerLeftHZ", 60 - LONG_WALL_IMG.getWidth(), windowHeight - 7, LONG_WALL_HZ_IMG));

        // border walls right side
        walls.add(new Wall("borderUpperRight", windowWidth - 7, -8, LONG_WALL_IMG));
        walls.add(new Wall("borderUpperRightHZ", windowWidth - 180, -53, LONG_WALL_HZ_IMG));
        walls.add(new Wall("borderLowerRight", windowWidth - 7, windowHeight - 180, LONG_WALL_IMG));
        walls.add(new Wall("borderLowerRightHZ", windowWidth - 180, windowHeight - 7, LONG_WALL_HZ_IMG));

        walls.add(new Wall("centerHZ1", (windowWidth - lWallHZWidth) / 2, (windowHeight - lWallHZHeight) / 2, LONG_WALL_HZ_IMG));
        walls.add(new Wall("centerVert1", (windowWidth - lWallWidth) / 2, (windowHeight - lWallHeight) / 2, LONG_WALL_IMG));
        walls.add(new Wall("centerVertUpper", (windowWidth - lWallHZWidth) / 2, windowHeight - 7, LONG_WALL_HZ_IMG));
        walls.add(new Wall("centerHZLower", (windowWidth - lWallHZWidth) / 2, -53, LONG_WALL_HZ_IMG));

        walls.add(new Wall("centerVert1", (windowWidth - CUBE_WALL_IMG.getWidth()) / 2, windowHeight - (3*CUBE_WALL_IMG.getHeight()), CUBE_WALL_IMG));
        walls.add(new Wall("centerVert1", (windowWidth - CUBE_WALL_IMG.getWidth()) / 2, CUBE_WALL_IMG.getHeight()*2, CUBE_WALL_IMG));

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
