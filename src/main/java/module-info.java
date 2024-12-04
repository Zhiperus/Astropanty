module org.astropanty {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens org.astropanty to javafx.fxml;

    exports org.astropanty;
}
