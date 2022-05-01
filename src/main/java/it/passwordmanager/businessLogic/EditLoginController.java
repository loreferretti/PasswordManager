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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class EditLoginController{

    private LoginController loginController;
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

    public void initialize(Login login) {

        loginController = new LoginController();
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
    protected void closeStage() {

        Stage stage = (Stage) (editWebsite.getScene().getWindow());

        stage.close();

    }

    @FXML
    protected void onBackButtonClick(ActionEvent event) {

        try {

            if(!editWebsite.getText().equals(login.getWebsite()) || !editWebsite.getText().equals(login.getUsername())
                    || !editPassword.getText().equals(login.getPassword())) {

                FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("back_without_save_confirm_dialog.fxml"));

                Stage dontSaveConfirmDialogStage = new Stage();

                Scene scene = new Scene(fxmlLoader.load());

                BackWithoutSaveConfirmDialogController backWithoutSaveConfirmDialogController = fxmlLoader.getController();

                backWithoutSaveConfirmDialogController.initialize(this);

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


}
