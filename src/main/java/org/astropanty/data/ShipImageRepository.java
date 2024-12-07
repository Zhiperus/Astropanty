package org.astropanty.data;

import java.util.List;

public class ShipImageRepository {
    private static final List<String> shipImages = List.of(
            ShipImageRepository.class.getResource("/org/astropanty/redShip.png").toExternalForm(),
            ShipImageRepository.class.getResource("/org/astropanty/cyanShip.png").toExternalForm(),
            ShipImageRepository.class.getResource("/org/astropanty/red2Ship.png").toExternalForm(),
            ShipImageRepository.class.getResource("/org/astropanty/cyan2Ship.png").toExternalForm());

    public static String getShipImagePath(int shipId) {
        return shipImages.get(shipId);
    }

    public static List<String> getAllShipImages() {
        return shipImages;
    }
}
