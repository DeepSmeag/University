package com.example.issproject.controllers;

//import com.example.issproject.HelloApplication;

import com.example.issproject.MainClass;
import com.example.issproject.models.AdminsEntity;
import com.example.issproject.repository.AdminRepo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginControl {

    AdminRepo adminRepo;

    @FXML
    Button loginButton;
    @FXML
    Button backButton;
    @FXML
    TextField usernameField;
    @FXML
    TextField passwordField;
    @FXML
    Text errorText;

    @FXML
    public void initialize() {
        adminRepo = new AdminRepo();
    }


    @FXML
    public void pressLoginButton() {
        System.out.println("login pressed");
        System.out.println(usernameField.getText());
        System.out.println(passwordField.getText());
        String username = usernameField.getText();
        String password = passwordField.getText();
        try {
            checkAdmin(username, password);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkAdmin(String username, String password) throws IOException {
        AdminsEntity admin = adminRepo.findAdmin(username, password);
        if(admin != null) {

        String basePath = System.getProperty("user.dir");
        String resourcePath = basePath + "/src/main/resources/com.example.issproject/adminControlPanel.fxml";
        File resourceFile = new File(resourcePath);
        if (resourceFile.exists()) {
            System.out.println("Resource found at: " + resourcePath);
        } else {
            System.out.println("Resource not found.");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(new URL("file:///" + resourcePath));
        AdminPanelControl controller = new AdminPanelControl();
        controller.setCurrentAdmin(admin);
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setTitle("Logged In");
        stage.setScene(scene);
        }
        else {
            System.out.println("Admin not found");
            errorText.setText("Admin not found");
        }
    }

    @FXML
    public void backPressed() throws IOException {
        System.out.println("back pressed");
//        goes back to main menu window
        String basePath = System.getProperty("user.dir");
        String resourcePath = basePath + "/src/main/resources/com.example.issproject/mainMenu.fxml";
        File resourceFile = new File(resourcePath);
        if (resourceFile.exists()) {
            System.out.println("Resource found at: " + resourcePath);
        } else {
            System.out.println("Resource not found.");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(new URL("file:///" + resourcePath));
        MainControl controller = new MainControl();
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load(), 426, 647);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setTitle("Main Menu");
        stage.setScene(scene);
    }
}
