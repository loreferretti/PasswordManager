package it.passwordmanager.businessLogic;

import it.passwordmanager.Launch;
import it.passwordmanager.domainModel.Login;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    private LoginController loginController;

    @FXML
    private TableView<Login> loginTable = new TableView<>();
    @FXML
    private TableColumn<Login, String> website = new TableColumn<>("website");
    @FXML
    private TableColumn<Login, String> username = new TableColumn<>("username");
    @FXML
    private TableColumn<Login, String> password = new TableColumn<>("password");
    @FXML
    private TextField searchField;

    public void initialize(URL location, ResourceBundle resource) {

        loginController = new LoginController();


        loginTable.setPlaceholder(new Label("The table is empty"));

        website.setCellValueFactory(new PropertyValueFactory<>("website"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));

        //TODO here get All the login from the database via the LoginController and set the table with .setItems()

        loginTable.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2) {
                showLoginClick();
            }
        });

        loginTable.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)) {
                showLoginClick();
            }
        });

    }



    private void refreshTable(ObservableList<Login> refreshList) {

        loginTable.setItems(refreshList);
        //FIXME think about the utility of this method
    }

    private void showLoginClick() {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("show_login_window.fxml"));

            Stage showStage = new Stage();

            Scene scene = new Scene(fxmlLoader.load());

            showStage.initModality(Modality.APPLICATION_MODAL);

            //TODO

        } catch (IOException e) {

            e.printStackTrace();
            System.out.println(e.getMessage());

        }
    }

    @FXML
    protected void handleShowLoginClick(ActionEvent event) {

        showLoginClick();

    }



}














