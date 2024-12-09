package org.astropanty.data;

import java.util.ArrayList;
import java.util.List;

public class ShipRepository {

    // Define a ShipAttributes class to hold all the attributes for a ship
    public static class ShipAttributes {
        private final String shipImagePath;
        private final String bulletImagePath;
        private final int shipSpeed;
        private final int bulletSpeed;
        private final int bulletDamage;

        public ShipAttributes(String shipImagePath, String bulletImagePath, int shipSpeed, int bulletSpeed, int bulletDamage) {
            this.shipImagePath = shipImagePath;
            this.bulletImagePath = bulletImagePath;
            this.shipSpeed = shipSpeed;
            this.bulletSpeed = bulletSpeed;
            this.bulletDamage = bulletDamage;
        }

        public String getShipImagePath() {
            return shipImagePath;
        }

        public String getBulletImagePath() {
            return bulletImagePath;
        }

        public int getShipSpeed() {
            return shipSpeed;
        }

        public int getBulletSpeed() {
            return bulletSpeed;
        }

        public int getBulletDamage() {
            return bulletDamage;
        }
    }

    // Repository of all ships with their attributes
    private static final List<ShipAttributes> ships = List.of(
            new ShipAttributes(
                    ShipRepository.class.getResource("/org/astropanty/redShip.png").toExternalForm(),
                    ShipRepository.class.getResource("/org/astropanty/redBullet.png").toExternalForm(),
                    5, 10, 5),
            new ShipAttributes(
                    ShipRepository.class.getResource("/org/astropanty/cyanShip.png").toExternalForm(),
                    ShipRepository.class.getResource("/org/astropanty/cyanBullet.png").toExternalForm(),
                    4, 7, 10),
            new ShipAttributes(
                    ShipRepository.class.getResource("/org/astropanty/red2Ship.png").toExternalForm(),
                    ShipRepository.class.getResource("/org/astropanty/red2Bullet.png").toExternalForm(),
                    6, 12, 3),
            new ShipAttributes(
                    ShipRepository.class.getResource("/org/astropanty/cyan2Ship.png").toExternalForm(),
                    ShipRepository.class.getResource("/org/astropanty/cyan2Bullet.png").toExternalForm(),
                    5, 5, 20)
    );

    public static ShipAttributes getShipAttributes(int shipId) {
        return ships.get(shipId);
    }

    public static List<String> getAllShipImages(){
        List<String> shipImages = new ArrayList<>();

        for(ShipAttributes shipAttr : ships){
            shipImages.add(shipAttr.shipImagePath);
        }
        
        return shipImages;
    }

    public static List<ShipAttributes> getAllShips() {
        return ships;
    }
}
