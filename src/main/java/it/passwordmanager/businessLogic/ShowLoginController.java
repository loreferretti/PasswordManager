package it.passwordmanager.businessLogic;

import it.passwordmanager.Launch;
import it.passwordmanager.domainModel.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;

import java.io.IOException;

public class ShowLoginController {

    private Login login;
    private LoginController loginController;

    @FXML
    private TextField website;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private PasswordField maskedPassword;
    @FXML
    private CheckBox showPassword;


    public void initialize(Login login) {

        this.loginController = new LoginController();
        this.login = login;


        website.setText(login.getWebsite());
        username.setText(login.getUsername());
        password.setText(login.getPassword());
        maskedPassword.setText(login.getPassword());


        password.setManaged(false);
        password.setVisible(false);

        password.managedProperty().bind(showPassword.selectedProperty());
        password.visibleProperty().bind(showPassword.selectedProperty());

        maskedPassword.managedProperty().bind(showPassword.selectedProperty().not());
        maskedPassword.visibleProperty().bind(showPassword.selectedProperty().not());

        password.textProperty().bindBidirectional(maskedPassword.textProperty());

    }

    @FXML



}