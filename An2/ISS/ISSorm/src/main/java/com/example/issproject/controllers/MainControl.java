package com.example.issproject.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainControl {
    @FXML
    Button reserveButton;
    @FXML
    Button adminLoginButton;


    @FXML
    public void reservePressed() throws MalformedURLException {
        String basePath = System.getProperty("user.dir");
        String resourcePath = basePath + "/src/main/resources/com.example.issproject/roomConfig.fxml";
        File resourceFile = new File(resourcePath);
        if (resourceFile.exists()) {
            System.out.println("Resource found at: " + resourcePath);
        } else {
            System.out.println("Resource not found.");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(new URL("file:///" + resourcePath));
        ShowRoomControl controller = new ShowRoomControl();
        fxmlLoader.setController(controller);
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 750, 514);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) reserveButton.getScene().getWindow();
        stage.setTitle("Show room");
        stage.setScene(scene);
//        stage.show();
    }
    @FXML
    public void pressLoginButton() throws IOException {
//        opens admin login window
        String basePath = System.getProperty("user.dir");
        String resourcePath = basePath + "/src/main/resources/com.example.issproject/loginMenu.fxml";
        File resourceFile = new File(resourcePath);
        if (resourceFile.exists()) {
            System.out.println("Resource found at: " + resourcePath);
        } else {
            System.out.println("Resource not found.");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(new URL("file:///" + resourcePath));
        LoginControl controller = new LoginControl();
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = (Stage) adminLoginButton.getScene().getWindow();
        stage.setTitle("Login Menu");
        stage.setScene(scene);
//        stage.show();


    }
}
