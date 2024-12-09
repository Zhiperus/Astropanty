package org.astropanty.data;

import javafx.scene.image.Image;
import org.astropanty.ui.game.entities.Wall;
import org.astropanty.ui.game.screens.GameProper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
     * Returns wall entities for Map 1 (named: The Wrap).
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

        // center walls
        walls.add(new Wall("centerHZ1", (windowWidth - lWallHZWidth) / 2, (windowHeight - lWallHZHeight) / 2, LONG_WALL_HZ_IMG));
        walls.add(new Wall("centerVert1", (windowWidth - lWallWidth) / 2, (windowHeight - lWallHeight) / 2, LONG_WALL_IMG));
        walls.add(new Wall("centerVertUpper", (windowWidth - lWallHZWidth) / 2, windowHeight - 7, LONG_WALL_HZ_IMG));
        walls.add(new Wall("centerHZLower", (windowWidth - lWallHZWidth) / 2, -53, LONG_WALL_HZ_IMG));

        walls.add(new Wall("centerCubeBotMid", (windowWidth - CUBE_WALL_IMG.getWidth()) / 2, windowHeight - (3*CUBE_WALL_IMG.getHeight()), CUBE_WALL_IMG));
        walls.add(new Wall("centerCubeTopMid", (windowWidth - CUBE_WALL_IMG.getWidth()) / 2, CUBE_WALL_IMG.getHeight()*2, CUBE_WALL_IMG));
       
        walls.add(new Wall("centerRectHZLeft", RECT_WALL_HZ_IMG.getWidth()*2+32, (windowHeight - RECT_WALL_HZ_IMG.getHeight()) / 2, RECT_WALL_HZ_IMG));
        walls.add(new Wall("centerRectHZRight", (windowWidth - RECT_WALL_HZ_IMG.getWidth())-272, (windowHeight - RECT_WALL_HZ_IMG.getHeight()) / 2, RECT_WALL_HZ_IMG));

        walls.add(new Wall("centerRectHZLeft", 0 - RECT_WALL_HZ_IMG.getWidth()/2, (windowHeight - RECT_WALL_HZ_IMG.getHeight()) / 2, RECT_WALL_HZ_IMG));
        walls.add(new Wall("centerRectHZRight", (windowWidth - RECT_WALL_HZ_IMG.getWidth())+RECT_WALL_HZ_IMG.getWidth()/2, (windowHeight - RECT_WALL_HZ_IMG.getHeight()) / 2, RECT_WALL_HZ_IMG));

        return walls;
    }

    /**
     * Returns wall entities for Map 2 (Funky Cubes) with random cube wall placement within a smaller area (40% of original ratio).
     */
    public static List<Wall> getMap2Walls() {
        List<Wall> walls = new ArrayList<>();
        Random random = new Random();
        int windowWidth = GameProper.WINDOW_WIDTH;
        int windowHeight = GameProper.WINDOW_HEIGHT;
    
        int smallerWidth = (int) (windowWidth * 0.6);
        int smallerHeight = (int) (windowHeight * 0.4);
    
        int offsetX = (windowWidth - smallerWidth) / 2;
        int offsetY = (windowHeight - smallerHeight) / 2;
    
        // border walls left side
        walls.add(new Wall("borderUpperLeft", 7 - LONG_WALL_IMG.getWidth(), -8, LONG_WALL_IMG));
        walls.add(new Wall("borderUpperLeftHZ", 60 - LONG_WALL_IMG.getWidth(), -53, LONG_WALL_HZ_IMG));
        walls.add(new Wall("borderUpperLeftHZ", LONG_WALL_IMG.getWidth()*2 + 60, -53, LONG_WALL_HZ_IMG));
        walls.add(new Wall("borderLowerLeft", 7 - LONG_WALL_IMG.getWidth(), windowHeight - 180, LONG_WALL_IMG));
        walls.add(new Wall("borderLowerLeftHZ", 60 - LONG_WALL_IMG.getWidth(), windowHeight - 7, LONG_WALL_HZ_IMG));
        walls.add(new Wall("borderLowerLeftHZ", LONG_WALL_IMG.getWidth()*2 + 60, windowHeight - 7, LONG_WALL_HZ_IMG));
    
        // border walls right side
        walls.add(new Wall("borderUpperRight", windowWidth - 7, -8, LONG_WALL_IMG));
        walls.add(new Wall("borderUpperRightHZ", windowWidth - 180, -53, LONG_WALL_HZ_IMG));
        walls.add(new Wall("borderUpperRightHZ", (windowWidth - 180)-LONG_WALL_HZ_IMG.getWidth(), -53, LONG_WALL_HZ_IMG));
        walls.add(new Wall("borderLowerRight", windowWidth - 7, windowHeight - 180, LONG_WALL_IMG));
        walls.add(new Wall("borderLowerRightHZ", windowWidth - 180, windowHeight - 7, LONG_WALL_HZ_IMG));
        walls.add(new Wall("borderLowerRightHZ", (windowWidth - 180)-LONG_WALL_HZ_IMG.getWidth(), windowHeight - 7, LONG_WALL_HZ_IMG));
    
        // randomized placement for cubes on the middle part of the map
        int minDistance = 100; // Minimum distance between walls
    
        for (int i = 0; i < 6; i++) {
            int x, y;
            boolean validPosition;
    
            do {
                x = random.nextInt(smallerWidth) + offsetX;
                y = random.nextInt(smallerHeight) + offsetY;
                validPosition = true;
    
                for (Wall wall : walls) {
                    int dx = (int) wall.getXPos() - x;
                    int dy = (int) wall.getYPos() - y;
                    if (Math.sqrt(dx * dx + dy * dy) < minDistance) {
                        validPosition = false;
                        break;
                    }
                }
            } while (!validPosition);
    
            walls.add(new Wall("cubeWall" + i, x, y, CUBE_WALL_IMG));
        }
    
        return walls;
    }
    
}
