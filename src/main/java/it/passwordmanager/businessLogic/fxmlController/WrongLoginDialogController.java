package it.passwordmanager.businessLogic.fxmlController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class WrongLoginDialogController {

    @FXML
    public void onRetryButtonClick(ActionEvent event) {

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        stage.close();

    }

}
