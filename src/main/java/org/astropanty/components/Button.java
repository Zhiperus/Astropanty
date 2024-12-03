package org.astropanty.components;

public class Button extends javafx.scene.control.Button {

    public Button(String text, Runnable action) {
        super(text); // Set the button text

        // Default style for the button
        setStyle("-fx-font-size: 18px; -fx-font-family: 'Orbitron'; "
                + "-fx-background-color: #444; -fx-text-fill: white; -fx-background-radius: 10;");

        // Hover effect
        setOnMouseEntered(event -> setStyle("-fx-font-size: 18px; -fx-font-family: 'Orbitron'; "
                + "-fx-background-color: #888; -fx-text-fill: white; -fx-background-radius: 10;"));
        setOnMouseExited(event -> setStyle("-fx-font-size: 18px; -fx-font-family: 'Orbitron'; "
                + "-fx-background-color: #444; -fx-text-fill: white; -fx-background-radius: 10;"));

        // Set the action to be executed on click
        setOnAction(event -> action.run());
    }
}
