module com.mycompany.clashroyale {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.clashroyale to javafx.fxml;
    exports com.mycompany.clashroyale;
}
