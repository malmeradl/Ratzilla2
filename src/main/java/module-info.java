module com.example.ratzilla2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ratzilla2 to javafx.fxml;
    exports com.example.ratzilla2;
}