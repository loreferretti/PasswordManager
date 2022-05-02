module it.passwordmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens it.passwordmanager to javafx.fxml;
    opens it.passwordmanager.businessLogic to javafx.fxml;
    opens it.passwordmanager.domainModel to javafx.base;

    exports it.passwordmanager;
}