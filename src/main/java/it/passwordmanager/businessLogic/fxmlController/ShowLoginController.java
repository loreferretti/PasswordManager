package it.passwordmanager.businessLogic.fxmlController;

import it.passwordmanager.Launch;
import it.passwordmanager.businessLogic.LoginController;
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
    private MainWindowController parentController;

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


    public void initialize(MainWindowController parentController, Login login) {

        disableFocus();

        this.parentController = parentController;
        this.loginController = LoginController.getInstance();
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
    private void disableFocus() {
        website.setFocusTraversable(false);
        username.setFocusTraversable(false);
        password.setFocusTraversable(false);
        maskedPassword.setFocusTraversable(false);
        showPassword.setFocusTraversable(false);
    }

    @FXML
    public void closeStage() {

        Stage stage = (Stage) (website.getScene().getWindow());

        stage.close();

    }


    @FXML
    public void onCopyWebsiteButtonClick(ActionEvent event) {

        Clipboard clipboard = Clipboard.getSystemClipboard();

        ClipboardContent content = new ClipboardContent();

        content.putString(website.getText());
        clipboard.setContent(content);

    }

    @FXML
    public void onCopyUsernameButtonClick(ActionEvent event) {

        Clipboard clipboard = Clipboard.getSystemClipboard();

        ClipboardContent content = new ClipboardContent();

        content.putString(username.getText());
        clipboard.setContent(content);

    }

    @FXML
    public void onCopyPasswordButtonClick(ActionEvent event) {

        Clipboard clipboard = Clipboard.getSystemClipboard();

        ClipboardContent content = new ClipboardContent();

        content.putString(password.getText());
        clipboard.setContent(content);

    }

    @FXML
    public void onCloseButtonClick(ActionEvent event) {

        closeStage();

    }

    @FXML
    public void onEditButtonClick(ActionEvent event) {

        try {

            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("edit_login_window.fxml"));

            Scene scene = new Scene(fxmlLoader.load());

            EditLoginController editLoginController = fxmlLoader.getController();

            editLoginController.initialize(parentController, login);

            stage.setTitle("Edit Login");
            stage.setScene(scene);
            stage.setResizable(false);

            stage.show();
            stage.centerOnScreen();

        } catch (IOException e) {

            e.printStackTrace();
            System.out.println(e.getMessage());

        }

    }

    @FXML
    public void onDeleteButtonClick(ActionEvent event) {

        try {

            Stage deleteConfirmStage = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("delete_confirm_dialog.fxml"));

            Scene scene = new Scene(fxmlLoader.load());

            DeleteConfirmOnShowLoginWindowDialogController deleteConfirmDialogController = fxmlLoader.getController();

            deleteConfirmDialogController.initialize(this, parentController, login);

            deleteConfirmStage.setTitle("Delete Confirmation");
            deleteConfirmStage.setScene(scene);
            deleteConfirmStage.setResizable(false);

            deleteConfirmStage.show();
            deleteConfirmStage.centerOnScreen();

        } catch (IOException e) {

            e.printStackTrace();
            System.out.println(e.getMessage());

        }

    }


}