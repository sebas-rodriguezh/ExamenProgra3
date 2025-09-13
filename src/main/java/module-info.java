module org.example.examenprograsrh {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.examenprograsrh to javafx.fxml;
    exports org.example.examenprograsrh;
}