package it.passwordmanager.businessLogic.fxmlController;

import it.passwordmanager.businessLogic.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class BackWithoutSaveOnEditConfirmDialogController {

    private LoginController loginController;

    private EditLoginController editLoginControllerParent;

    public void initialize(EditLoginController parentController) {

        loginController = LoginController.getInstance();

        this.editLoginControllerParent = parentController;

    }

    @FXML
    public void onNoButtonClick(ActionEvent event) {

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        stage.close();

    }

    @FXML
    public void onYesButtonClick(ActionEvent event) {

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        stage.close();

        editLoginControllerParent.closeStage();

    }

}