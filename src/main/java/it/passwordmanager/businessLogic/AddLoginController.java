package it.passwordmanager.businessLogic;

import it.passwordmanager.Launch;
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

public class AddLoginController {

    //FIXME fix the focus on the elements of the scene

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
    @FXML
    private Label errorLabel;


    public void initialize(MainWindowController parentController) {

        disableFocus();

        loginController = LoginController.getInstance();

        this.parentController = parentController;


        password.setManaged(false);
        password.setVisible(false);

        password.managedProperty().bind(showPassword.selectedProperty());
        password.visibleProperty().bind(showPassword.selectedProperty());

        maskedPassword.managedProperty().bind(showPassword.selectedProperty().not());
        maskedPassword.visibleProperty().bind(showPassword.selectedProperty().not());

        password.textProperty().bindBidirectional(maskedPassword.textProperty());

    }

    protected void closeStage() {

        Stage stage = (Stage) (website.getScene().getWindow());

        stage.close();

    }

    @FXML
    public void onGeneratePasswordButtonClick(ActionEvent event) {

        PasswordGenerator passwordGenerator = new PasswordGenerator();

        maskedPassword.setText(passwordGenerator.generate());

    }

    @FXML
    protected void onBackButtonClick(ActionEvent event) {

        try {

            if(!website.getText().equals("") || !username.getText().equals("") || !password.getText().equals("")) {

                FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("back_without_save_confirm_dialog.fxml"));

                Stage dontSaveConfirmDialogStage = new Stage();

                Scene scene = new Scene(fxmlLoader.load());

                BackWithoutSaveOnAddConfirmDialogController backWithoutSaveOnAddConfirmDialogController = fxmlLoader.getController();

                backWithoutSaveOnAddConfirmDialogController.initialize(this);

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

        if(!website.getText().equals("") && !username.getText().equals("") && !password.getText().equals("")) {
            if(!loginController.addLogin(new Login(website.getText(), username.getText(), password.getText()))) {
                errorLabel.setTextFill(Color.RED);
                errorLabel.setText("This login already exists!");

                website.clear();
                username.clear();
                password.clear();
            }
            else{

                closeStage();

                parentController.getAll();
            }
        }
        else {
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText("It cannot be inserted a login with blank fields");

            website.clear();
            username.clear();
            password.clear();
        }

    }

    @FXML
    private void disableFocus() {
        website.setFocusTraversable(false);
        username.setFocusTraversable(false);
        password.setFocusTraversable(false);
        maskedPassword.setFocusTraversable(false);
        showPassword.setFocusTraversable(false);
        errorLabel.setFocusTraversable(false);
    }
}




























