package com.example.issproject.controllers;

//import com.example.issproject.HelloApplication;

import com.example.issproject.HelloApplication;
import com.example.issproject.model.AdminsEntity;
import com.example.issproject.repository.AdminRepo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;

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

        checkAdmin(username,password);
    }

    public void checkAdmin(String username, String password) {
        AdminsEntity admin = adminRepo.findAdmin(username, password);
        System.out.println(admin);
    }

    @FXML
    public void backPressed() throws IOException {
        System.out.println("back pressed");
//        goes back to main menu window
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainMenu.fxml"));
        MainControl controller = new MainControl();
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load(), 426, 647);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setTitle("Main Menu");
        stage.setScene(scene);
    }
}
