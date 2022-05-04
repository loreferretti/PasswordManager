package it.passwordmanager.businessLogic.fxmlController;

import it.passwordmanager.Launch;
import it.passwordmanager.businessLogic.LoginController;
import it.passwordmanager.businessLogic.PasswordGenerator;
import it.passwordmanager.domainModel.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class EditLoginController{

    private LoginController loginController;
    private MainWindowController parentController;
    private Login login;

    @FXML
    private TextField editWebsite;
    @FXML
    private TextField editUsername;
    @FXML
    private TextField editPassword;
    @FXML
    private PasswordField editPasswordMasked;
    @FXML
    private CheckBox showPassword;
    @FXML
    private Label errorLabel;

    public void initialize(MainWindowController parentController, Login login) {

        disableFocus();

        this.parentController = parentController;
        loginController = LoginController.getInstance();
        this.login = login;


        editWebsite.setText(login.getWebsite());
        editUsername.setText(login.getUsername());
        editPassword.setText(login.getPassword());
        editPasswordMasked.setText(login.getPassword());


        editPassword.setManaged(false);
        editPassword.setVisible(false);

        editPassword.managedProperty().bind(showPassword.selectedProperty());
        editPassword.visibleProperty().bind(showPassword.selectedProperty());

        editPasswordMasked.managedProperty().bind(showPassword.selectedProperty().not());
        editPasswordMasked.visibleProperty().bind(showPassword.selectedProperty().not());

        editPassword.textProperty().bindBidirectional(editPasswordMasked.textProperty());

    }

    @FXML
    private void disableFocus() {
//        editWebsite.setFocusTraversable(false);
//        editUsername.setFocusTraversable(false);
        editPassword.setFocusTraversable(false);
//        editPasswordMasked.setFocusTraversable(false);
        showPassword.setFocusTraversable(false);
        errorLabel.setFocusTraversable(false);
    }

    @FXML
    public void closeStage() {

        Stage stage = (Stage) (editWebsite.getScene().getWindow());

        stage.close();

    }

    @FXML
    public void onBackButtonClick(ActionEvent event) {

        try {

            if(!editWebsite.getText().equals(login.getWebsite()) || !editUsername.getText().equals(login.getUsername())
                    || !editPassword.getText().equals(login.getPassword())) {

                FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("back_without_save_confirm_dialog_on_edit.fxml"));

                Stage dontSaveConfirmDialogStage = new Stage();

                Scene scene = new Scene(fxmlLoader.load());

                BackWithoutSaveOnEditConfirmDialogController backWithoutSaveOnEditConfirmDialogController = fxmlLoader.getController();

                backWithoutSaveOnEditConfirmDialogController.initialize(this);

                dontSaveConfirmDialogStage.setScene(scene);

                dontSaveConfirmDialogStage.setTitle("Warning");
                dontSaveConfirmDialogStage.setResizable(false);
                dontSaveConfirmDialogStage.initModality(Modality.APPLICATION_MODAL);

                dontSaveConfirmDialogStage.show();
                dontSaveConfirmDialogStage.centerOnScreen();

            }
            else {

                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

                stage.close();

            }

        } catch (IOException e) {

            e.printStackTrace();
            System.out.println(e.getMessage());

        }

    }

    @FXML
    public void onGeneratePasswordButtonClick(ActionEvent event) {

        editPassword.setText(PasswordGenerator.generate());

    }

    @FXML
    public void onSaveButtonClick(ActionEvent event) {
        String website = login.getWebsite();
        String username = login.getUsername();
        String password = login.getPassword();

        if (!editWebsite.getText().equals(website) || !editUsername.getText().equals(username)
                || !editPassword.getText().equals(password) ) {

            if(!editWebsite.getText().equals("") &&
                !editUsername.getText().equals("") && !editPassword.getText().equals("")) {

                login.setWebsite(editWebsite.getText());
                login.setUsername(editUsername.getText());
                login.setPassword(editPassword.getText());

                if(!loginController.updateLogin(login)) {

                    errorLabel.setTextFill(Color.RED);
                    errorLabel.setText("This login already exists!");

                }
                else {

                    closeStage();

                    parentController.getAll();

                }

            }
            else {
                errorLabel.setTextFill(Color.RED);
                errorLabel.setText("It cannot be inserted a login with blank fields");

                editWebsite.setText(website);
                editUsername.setText(username);
                editPassword.setText(password);
            }


        }
        else
            closeStage();

    }


}
