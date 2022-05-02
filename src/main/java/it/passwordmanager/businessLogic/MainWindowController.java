package it.passwordmanager.businessLogic;

import it.passwordmanager.Launch;
import it.passwordmanager.domainModel.Login;
import javafx.collections.FXCollections;
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

    private ObservableList<Login> list = FXCollections.observableArrayList();

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

        for (int i = 0; i <= 25; i++) {
            String site = "website" + i;
            String user = "username" + i;
            String pass = "password" + i;
            list.add(new Login(site, user, pass));
        }

        loginTable.setItems(list);

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
            showStage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(fxmlLoader.load());

            Login login = loginTable.getSelectionModel().getSelectedItem();

            ShowLoginController showLoginController = fxmlLoader.getController();

            showLoginController.initialize(login);

            showStage.setTitle("Show Login");
            showStage.setScene(scene);
            showStage.setResizable(false);

            showStage.show();
            showStage.centerOnScreen();

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
            editStage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(fxmlLoader.load());

            Login login = loginTable.getSelectionModel().getSelectedItem();

            EditLoginController editLoginController = fxmlLoader.getController();

            editLoginController.initialize(login);

            editStage.setTitle("Edit login");
            editStage.setScene(scene);
            editStage.setResizable(false);

            editStage.show();
            editStage.centerOnScreen();

        } catch (IOException e) {

            e.printStackTrace();
            System.out.println(e.getMessage());

        }

    }

    @FXML
    protected void handleDeleteLoginClick(ActionEvent event) {

        Login login = loginTable.getSelectionModel().getSelectedItem();

        if(loginController.deleteLogin(login));
            //refreshTable

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

        //loginController.searchByWebsite(searchString); we take the result of this read and we set the items of the table

        //TODO recall the loginController search method

    }

    @FXML
    protected void onAddButtonClick(ActionEvent event) {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("add_login_window.fxml"));

            Stage addStage = new Stage();
            addStage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(fxmlLoader.load());

            addStage.setTitle("Add login");
            addStage.setScene(scene);
            addStage.setResizable(false);

            addStage.show();
            addStage.centerOnScreen();


        } catch (IOException e) {

            e.printStackTrace();
            System.out.println(e.getMessage());

        }
    }

    @FXML
    protected void onQuitButtonClick(ActionEvent event) {

        javafx.application.Platform.exit();

    }

    @FXML
    protected void onAboutButtonClick(ActionEvent event) {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("credits_dialog.fxml"));

            Stage creditsStage = new Stage();

            Scene scene = new Scene(fxmlLoader.load());

            creditsStage.initModality(Modality.APPLICATION_MODAL);

            creditsStage.setResizable(false);
            creditsStage.setTitle("Credits");
            creditsStage.setScene(scene);

            creditsStage.show();
            creditsStage.centerOnScreen();

        } catch (IOException e) {

            e.printStackTrace();
            System.out.println(e.getMessage());

        }

    }

}














