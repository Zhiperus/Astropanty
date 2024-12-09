package org.astropanty.data;

import java.util.List;

public class ShipRepository {

    // Define a ShipAttributes class to hold all the attributes for a ship
    public static class ShipAttributes {
        private final String shipImagePath;
        private final String bulletImagePath;
        private final int shipSpeed;
        private final int bulletSpeed;

        public ShipAttributes(String shipImagePath, String bulletImagePath, int shipSpeed, int bulletSpeed) {
            this.shipImagePath = shipImagePath;
            this.bulletImagePath = bulletImagePath;
            this.shipSpeed = shipSpeed;
            this.bulletSpeed = bulletSpeed;
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
    }

    // Repository of all ships with their attributes
    private static final List<ShipAttributes> ships = List.of(
            new ShipAttributes(
                    ShipRepository.class.getResource("/org/astropanty/redShip.png").toExternalForm(),
                    ShipRepository.class.getResource("/org/astropanty/redBullet.png").toExternalForm(),
                    5, 10),
            new ShipAttributes(
                    ShipRepository.class.getResource("/org/astropanty/cyanShip.png").toExternalForm(),
                    ShipRepository.class.getResource("/org/astropanty/cyanBullet.png").toExternalForm(),
                    4, 9),
            new ShipAttributes(
                    ShipRepository.class.getResource("/org/astropanty/red2Ship.png").toExternalForm(),
                    ShipRepository.class.getResource("/org/astropanty/red2Bullet.png").toExternalForm(),
                    6, 12),
            new ShipAttributes(
                    ShipRepository.class.getResource("/org/astropanty/cyan2Ship.png").toExternalForm(),
                    ShipRepository.class.getResource("/org/astropanty/cyan2Bullet.png").toExternalForm(),
                    5, 11)
    );

    public static ShipAttributes getShipAttributes(int shipId) {
        return ships.get(shipId);
    }

    public static List<ShipAttributes> getAllShips() {
        return ships;
    }
}
