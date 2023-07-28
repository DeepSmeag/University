package com.example.issproject.controllers;


import com.example.issproject.HelloApplication;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainControl {
    @FXML
    Button reserveButton;
    @FXML
    Button adminLoginButton;


    @FXML
    public void reservePressed() {
        System.out.println("reserve pressed");
    }
    @FXML
    public void pressLoginButton() throws IOException {
//        opens admin login window
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("loginMenu.fxml"));
        LoginControl controller = new LoginControl();
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = (Stage) adminLoginButton.getScene().getWindow();
        stage.setTitle("Login Menu");
        stage.setScene(scene);
//        stage.show();


    }
}
