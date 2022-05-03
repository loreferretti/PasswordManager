package it.passwordmanager.businessLogic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class CreditsDialogController {

    @FXML
    public void onCloseButtonClick(ActionEvent event) {

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        stage.close();

    }

}
