package it.passwordmanager.businessLogic;

import it.passwordmanager.Launch;
import it.passwordmanager.domainModel.Login;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.List;
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

        loginController = LoginController.getInstance();

        disableFocus();

    }

    @FXML
    protected void onEnterButtonClick(ActionEvent event) throws IOException {

        if(password.getText().equals(reinsertedPassword.getText())) { //TODO check also the length of the password

            loginController.setPassword(password.getText());

            List<Login> logins = loginController.getAll();

            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

            stage.setWidth(960);
            stage.setHeight(540);

            FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("main_window.fxml"));

            Scene scene = new Scene(fxmlLoader.load());

            MainWindowController mainWindowController = fxmlLoader.getController();

            mainWindowController.initialize(logins);

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

    @FXML
    protected void onAboutButtonClick(ActionEvent event) throws IOException{

        FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("credits_dialog.fxml"));

        Stage creditsStage = new Stage();

        Scene scene = new Scene(fxmlLoader.load());

        creditsStage.setTitle("Credits");
        creditsStage.setScene(scene);
        creditsStage.setResizable(false);

        creditsStage.show();
        creditsStage.centerOnScreen();

    }

    @FXML
    private void disableFocus() {
        password.setFocusTraversable(false);
        reinsertedPassword.setFocusTraversable(false);
        label.setFocusTraversable(false);
    }

}