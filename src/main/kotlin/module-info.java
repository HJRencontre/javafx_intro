module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires java.desktop;
    requires webcam.capture;
    requires org.json;


    opens org.example.demo to javafx.fxml;
    exports org.example.demo;
}