package it.passwordmanager.businessLogic;

import it.passwordmanager.Launch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FirstLoginSetMasterPasswordController implements Initializable {

    LoginController loginController;

    @FXML
    private PasswordField password;
    @FXML
    private PasswordField reinsertedPassword;
    @FXML
    private Label label;

    @Override
    public void initialize(URL location, ResourceBundle resource) {

        loginController = new LoginController();

    }

    @FXML
    protected void onEnterButtonClick(ActionEvent event) throws IOException {

        if(password.getText().equals(reinsertedPassword.getText())) {

            loginController.storeAndEncryptPassword(reinsertedPassword.getText());

            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("main_window.fxml"));

            Scene scene = new Scene(fxmlLoader.load());

            stage.setTitle("Password Manager");
            stage.setScene(scene);

            stage.show();
            stage.centerOnScreen();

        }
        else {

            label.setTextFill(Color.RED);
            label.setText("Passwords do not match. Try again!");

            password.clear();
            reinsertedPassword.clear();

        }
    }

    @FXML
    protected void onQuitButtonClick(ActionEvent event) {

        javafx.application.Platform.exit();

    }



}
