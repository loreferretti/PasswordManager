package it.passwordmanager.businessLogic;

import it.passwordmanager.Launch;
import it.passwordmanager.domainModel.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MasterLoginController implements Initializable {

    private LoginController loginController;

    @FXML
    private PasswordField password;
    @FXML
    private Button enter;
    @FXML
    private Button quit;
    @FXML
    private Button about;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loginController = LoginController.getInstance();

        disableFocus();

    }

    @FXML
    public void onEnterButtonClick(ActionEvent event) throws IOException {

        String masterPassword = password.getText();

        loginController.setPassword(masterPassword);

        List<Login> logins = loginController.getAll();

        if(logins != null) {

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

            password.clear();

            Stage stage = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("wrong_login_dialog.fxml"));

            Scene scene = new Scene(fxmlLoader.load());

            stage.setTitle("Wrong Login");
            stage.setResizable(false);
            stage.setScene(scene);

            stage.show();
            stage.centerOnScreen();

        }

    }

    @FXML
    public void onQuitButtonClick(ActionEvent event) {

        javafx.application.Platform.exit();

    }

    @FXML
    public void onAboutButtonClick(ActionEvent event) throws IOException {

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
    public void disableFocus() {
        password.setFocusTraversable(false);
        enter.setFocusTraversable(false);
        quit.setFocusTraversable(false);
        about.setFocusTraversable(false);
    }

}
