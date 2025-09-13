module org.example.examenprograsrh {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.xml.bind;

    opens org.example.examenprograsrh.Datos to jakarta.xml.bind;
    opens org.example.examenprograsrh.Logica to jakarta.xml.bind;

    exports org.example.examenprograsrh.Controller;
    opens org.example.examenprograsrh.Controller to javafx.fxml;

    exports org.example.examenprograsrh.Datos;
    exports org.example.examenprograsrh.Logica;

    exports org.example.examenprograsrh.Model;
    opens org.example.examenprograsrh.Model to javafx.fxml;

    opens org.example.examenprograsrh to javafx.fxml;
    exports org.example.examenprograsrh;
}