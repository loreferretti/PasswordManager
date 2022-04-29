package it.passwordmanager;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Launch extends Application {
    @Override
    public void start(Stage primaryStage) {

        try {

            FXMLLoader fxmlLoader ;

            File fileProperties = new File("src/main/resources/.passwordManager.properties");
            fileProperties.createNewFile();

            FileReader reader = new FileReader(fileProperties);

            Properties properties = new Properties();
            properties.load(reader);

            if(properties.getProperty("masterPassword") != null && properties.getProperty("salt") != null) {

                fxmlLoader = new FXMLLoader(Launch.class.getResource("master_login.fxml"));

                Scene scene = new Scene(fxmlLoader.load());

                primaryStage.setTitle("Password Manager");
                primaryStage.setResizable(false);
                primaryStage.setScene(scene);

            }
            else {

                fxmlLoader = new FXMLLoader(Launch.class.getResource("first_login_set_master_password.fxml"));

                Scene scene = new Scene(fxmlLoader.load());

                primaryStage.setTitle("Password Manager");
                primaryStage.setResizable(false);
                primaryStage.setScene(scene);

            }

            reader.close();

            primaryStage.show();
            primaryStage.centerOnScreen();

        } catch(IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) {
        launch();
    }
}

