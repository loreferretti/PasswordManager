package it.passwordmanager.businessLogic.fxmlController;

import it.passwordmanager.Launch;
import it.passwordmanager.businessLogic.LoginController;
import it.passwordmanager.domainModel.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    @FXML
    private Button enter;
    @FXML
    private Button quit;
    @FXML
    private Button about;

    @Override
    public void initialize(URL location, ResourceBundle resource) {

        loginController = LoginController.getInstance();

        disableFocus();

        password.textProperty().addListener((observableValue, oldValue, newValue) -> onPasswordTextChanged(newValue));
        reinsertedPassword.textProperty().addListener((observableValue, oldValue, newValue) -> onReinsertedPasswordTextChanged(newValue));
        enter.setDisable(true);

    }

    @FXML
    public void onEnterButtonClick(ActionEvent event) throws IOException {

            if(password.getText().length() >= 8) {

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
                label.setText("Password length is less than 8 characters. Try again!");

                password.clear();
                reinsertedPassword.clear();
            }

    }

    @FXML
    public void onQuitButtonClick(ActionEvent event) {

        javafx.application.Platform.exit();

    }

    @FXML
    public void onAboutButtonClick(ActionEvent event) throws IOException{

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
        enter.setFocusTraversable(false);
        quit.setFocusTraversable(false);
        about.setFocusTraversable(false);
    }

    @FXML
    public void enableFocus() {
        password.setFocusTraversable(true);
        reinsertedPassword.setFocusTraversable(true);
        enter.setFocusTraversable(true);
        quit.setFocusTraversable(true);
        about.setFocusTraversable(true);
    }

    @FXML
    public void onPasswordTextChanged(String pass) {
        if(pass.length() > 0) {
            enableFocus();
            if(!pass.equals(reinsertedPassword.getText()) && reinsertedPassword.getText().length() > 7){
                label.setTextFill(Color.RED);
                label.setText("Passwords do not match");
                enter.setDisable(true);
            }
            else{
                if(pass.length() > 7) {
                    if(!pass.equals(reinsertedPassword.getText())) {
                        label.setTextFill(Color.RED);
                        label.setText("Passwords do not match");
                        enter.setDisable(true);
                    }
                    else {
                        label.setText("");
                        enter.setDisable(false);
                    }
                }
                else {
                    label.setTextFill(Color.RED);
                    label.setText("Password is too short, minimum 8 characters");
                    enter.setDisable(true);
                }
            }
        }
        else{
            enter.setDisable(true);
            disableFocus();
            label.setText("");
        }
    }

    @FXML
    public void onReinsertedPasswordTextChanged(String reinsertedPass) {
        if(reinsertedPass.equals(password.getText()) && reinsertedPass.length() > 7) {
            label.setText("");
            enter.setDisable(false);
        }
        else {
            enter.setDisable(true);
            label.setTextFill(Color.RED);
            label.setText("Passwords do not match");
        }
    }

}
