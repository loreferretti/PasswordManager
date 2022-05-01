package it.passwordmanager.businessLogic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class BackWithoutSaveConfirmDialogController {

    private LoginController loginController;

    private AddLoginController addLoginControllerParent;
    private EditLoginController editLoginControllerParent;

    public void initialize(AddLoginController parentController) {

        loginController = new LoginController();

        this.addLoginControllerParent = parentController;

    }

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

        addLoginControllerParent.closeStage();

    }

}
