module it.passwordmanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.passwordmanager to javafx.fxml;
    exports it.passwordmanager;
}