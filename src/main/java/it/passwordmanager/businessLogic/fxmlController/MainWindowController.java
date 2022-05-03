package it.passwordmanager.businessLogic.fxmlController;

import it.passwordmanager.Launch;
import it.passwordmanager.businessLogic.LoginController;
import it.passwordmanager.domainModel.Login;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.util.List;

public class MainWindowController {

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

    public void initialize(List<Login> list) {

        disableFocus();

        loginController = LoginController.getInstance();


        loginTable.setPlaceholder(new Label("The table is empty"));

        website.setCellValueFactory(new PropertyValueFactory<>("website"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));


        setTableViewContent(list);




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

        searchField.textProperty().addListener((observableValue, oldValue, newValue) -> onSearchTextChanged(newValue));

    }


    public void getAll() {

        List<Login> arrayList = loginController.getAll();

        setTableViewContent(arrayList);

    }

    public void setTableViewContent(List<Login> list) {

        ObservableList<Login> tableViewlist = FXCollections.observableArrayList(list);

        loginTable.setItems(tableViewlist);

    }

    private void showLoginClick() {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("show_login_window.fxml"));

            Stage showStage = new Stage();
            showStage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(fxmlLoader.load());

            Login login = loginTable.getSelectionModel().getSelectedItem();

            ShowLoginController showLoginController = fxmlLoader.getController();

            showLoginController.initialize(this, login);

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
    public void handleShowLoginClick(ActionEvent event) {

        showLoginClick();

    }

    @FXML
    public void handleEditLoginClick(ActionEvent event) {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("edit_login_window.fxml"));

            Stage editStage = new Stage();
            editStage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(fxmlLoader.load());

            Login login = loginTable.getSelectionModel().getSelectedItem();

            EditLoginController editLoginController = fxmlLoader.getController();

            editLoginController.initialize(this, login);

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
    public void handleDeleteLoginClick(ActionEvent event) {

        Login login = loginTable.getSelectionModel().getSelectedItem();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("delete_confirm_dialog_main_window.fxml"));

            Stage stage = new Stage();

            Scene scene = new Scene(fxmlLoader.load());

            DeleteConfirmOnMainWindowDialogController deleteConfirmOnMainWindowDialogController = fxmlLoader.getController();

            deleteConfirmOnMainWindowDialogController.initialize(this, login);

            stage.setTitle("Delete Confirm");
            stage.setScene(scene);
            stage.setResizable(false);

            stage.show();
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }



    }

    @FXML
    public void handleCopyWebsiteClick(ActionEvent event) {

        String website = loginTable.getSelectionModel().getSelectedItem().getWebsite();

        Clipboard clipboard = Clipboard.getSystemClipboard();

        ClipboardContent content = new ClipboardContent();
        content.putString(website);

        clipboard.setContent(content);

    }

    @FXML
    public void handleCopyUsernameClick(ActionEvent event) {

        String website = loginTable.getSelectionModel().getSelectedItem().getUsername();

        Clipboard clipboard = Clipboard.getSystemClipboard();

        ClipboardContent content = new ClipboardContent();
        content.putString(website);

        clipboard.setContent(content);

    }

    @FXML
    public void handleCopyPasswordClick(ActionEvent event) {

        String website = loginTable.getSelectionModel().getSelectedItem().getPassword();

        Clipboard clipboard = Clipboard.getSystemClipboard();

        ClipboardContent content = new ClipboardContent();
        content.putString(website);

        clipboard.setContent(content);

    }

    @FXML
    public void onSearchTextChanged(String searchString) {

        setTableViewContent(loginController.searchByWebsite(searchString));

    }

    @FXML
    public void onAddButtonClick(ActionEvent event) {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("add_login_window.fxml"));

            Stage addStage = new Stage();
            addStage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(fxmlLoader.load());

            AddLoginController addLoginController = fxmlLoader.getController();

            addLoginController.initialize(this);

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
    public void onQuitButtonClick(ActionEvent event) {

        javafx.application.Platform.exit();

    }

    @FXML
    public void onAboutButtonClick(ActionEvent event) {

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

    @FXML
    private void disableFocus() {
        searchField.setFocusTraversable(false);
        loginTable.setFocusTraversable(false);
    }

}














