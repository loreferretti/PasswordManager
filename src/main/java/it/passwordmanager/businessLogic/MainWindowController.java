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

    @FXML
    protected void handleEditLoginClick(ActionEvent event) {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("edit_login_window.fxml"));

            Stage editStage = new Stage();

            Scene scene = new Scene(fxmlLoader.load());

            editStage.initModality(Modality.APPLICATION_MODAL);



        } catch (IOException e) {

            e.printStackTrace();
            System.out.println(e.getMessage());

        }

    }

    @FXML
    protected void handleDeleteLoginClick(ActionEvent event) {

        Login login = loginTable.getSelectionModel().getSelectedItem();

        loginController.deleteLogin(login);

        //refreshTable

        //FIXME think about how to do the refresh of the list, whether do it in loginDao model or similar or do it here

    }

    @FXML
    protected void handleCopyWebsiteClick(ActionEvent event) {

        String website = loginTable.getSelectionModel().getSelectedItem().getWebsite();

        Clipboard clipboard = Clipboard.getSystemClipboard();

        ClipboardContent content = new ClipboardContent();
        content.putString(website);

        clipboard.setContent(content);

    }

    @FXML
    protected void handleCopyUsernameClick(ActionEvent event) {

        String website = loginTable.getSelectionModel().getSelectedItem().getUsername();

        Clipboard clipboard = Clipboard.getSystemClipboard();

        ClipboardContent content = new ClipboardContent();
        content.putString(website);

        clipboard.setContent(content);

    }

    @FXML
    protected void handleCopyPasswordClick(ActionEvent event) {

        String website = loginTable.getSelectionModel().getSelectedItem().getPassword();

        Clipboard clipboard = Clipboard.getSystemClipboard();

        ClipboardContent content = new ClipboardContent();
        content.putString(website);

        clipboard.setContent(content);

    }

    @FXML
    protected void onSearchButtonClick(ActionEvent event) {

        String searchString = searchField.getText();

        //TODO recall the loginController search method

    }

    @FXML
    protected void onAddButtonClick(ActionEvent event) {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("add_login_window.fxml"));

            Stage addStage = new Stage();

            Scene scene = new Scene(fxmlLoader.load());

            addStage.initModality(Modality.APPLICATION_MODAL);

            //TODO

        } catch (IOException e) {

            e.printStackTrace();
            System.out.println(e.getMessage());

        }
    }


}














