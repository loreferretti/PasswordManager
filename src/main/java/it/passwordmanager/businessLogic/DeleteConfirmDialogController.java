package it.passwordmanager.businessLogic;

import it.passwordmanager.domainModel.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class DeleteConfirmDialogController {

    private LoginController loginController;
    private MainWindowController parentController;
    private Login login;

    private ShowLoginController showLoginController;

    public void initialize(ShowLoginController showLoginController, MainWindowController parentController, Login login) {

        this.login = login;

        loginController = LoginController.getInstance();

        this.parentController = parentController;

        this.showLoginController = showLoginController;

    }

    @FXML
    protected void onNoButtonClick(ActionEvent event) {

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        stage.close();

    }

    @FXML
    protected void onYesButtonClick(ActionEvent event) {

        loginController.deleteLogin(login);

        parentController.getAll();

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        stage.close();

        showLoginController.closeStage();



    }

}
