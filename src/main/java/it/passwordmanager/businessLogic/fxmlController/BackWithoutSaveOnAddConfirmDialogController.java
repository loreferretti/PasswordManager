package it.passwordmanager.businessLogic.fxmlController;

import it.passwordmanager.businessLogic.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class BackWithoutSaveOnAddConfirmDialogController {

    private LoginController loginController;

    private AddLoginController addLoginControllerParent;

    public void initialize(AddLoginController parentController) {

        loginController = LoginController.getInstance();

        this.addLoginControllerParent = parentController;

    }

    @FXML
    protected void onNoButtonClick(ActionEvent event) {

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        stage.close();

    }

    @FXML
    protected void onYesButtonClick(ActionEvent event) {

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        stage.close();

        addLoginControllerParent.closeStage();

    }

}
