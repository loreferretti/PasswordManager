module it.passwordmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens it.passwordmanager to javafx.fxml;
    exports it.passwordmanager;
}