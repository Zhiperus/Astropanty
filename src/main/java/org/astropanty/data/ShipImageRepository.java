package org.astropanty.data;

import java.util.List;

public class ShipImageRepository {
    private static final List<String> shipImages = List.of(
            ShipImageRepository.class.getResource("/org/astropanty/myShip.png").toExternalForm(),
            ShipImageRepository.class.getResource("/org/astropanty/yourShip.png").toExternalForm());

    public static String getShipImagePath(int shipId) {
        return shipImages.get(shipId);
    }

    public static List<String> getAllShipImages() {
        return shipImages;
    }
}
