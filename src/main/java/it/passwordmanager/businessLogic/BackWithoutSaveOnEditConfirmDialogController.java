package it.passwordmanager.businessLogic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class BackWithoutSaveOnEditConfirmDialogController {

    private LoginController loginController;

    private EditLoginController editLoginControllerParent;

    public void initialize(EditLoginController parentController) {

        loginController = new LoginController();

        this.editLoginControllerParent = parentController;

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

        editLoginControllerParent.closeStage();

    }

}