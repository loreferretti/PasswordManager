package it.passwordmanager.businessLogic.fxmlController;

import it.passwordmanager.businessLogic.LoginController;
import it.passwordmanager.domainModel.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class DeleteConfirmOnMainWindowDialogController {

    private LoginController loginController;
    private MainWindowController parentController;
    private Login login;

    public void initialize(MainWindowController parentController, Login login) {

        this.login = login;

        loginController = LoginController.getInstance();

        this.parentController = parentController;

    }

    @FXML
    public void onNoButtonClick(ActionEvent event) {

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        stage.close();

    }

    @FXML
    public void onYesButtonClick(ActionEvent event) {

        loginController.deleteLogin(login);

        parentController.getAll();

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        stage.close();

    }

}

