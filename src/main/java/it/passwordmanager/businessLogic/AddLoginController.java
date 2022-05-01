package it.passwordmanager.businessLogic;

import it.passwordmanager.Launch;
import it.passwordmanager.domainModel.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddLoginController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loginController = new LoginController();


        password.setManaged(false);
        password.setVisible(false);

        password.managedProperty().bind(showPassword.selectedProperty());
        password.visibleProperty().bind(showPassword.selectedProperty());

        maskedPassword.managedProperty().bind(showPassword.selectedProperty().not());
        maskedPassword.managedProperty().bind(showPassword.selectedProperty().not());

        password.textProperty().bindBidirectional(maskedPassword.textProperty());

    }

    protected void closeStage() {

        Stage stage = (Stage) (website.getScene().getWindow());

        stage.close();

    }

    @FXML
    protected void onBackButtonClick(ActionEvent event) {

        try {

            if(!website.getText().equals("") || !username.getText().equals("") || !password.getText().equals("")) {

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

    @FXML
    protected void onSaveButtonClick(ActionEvent event) {

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        stage.close();

        loginController.addLogin(new Login(website.getText(), username.getText(), password.getText()));

    }
}




























