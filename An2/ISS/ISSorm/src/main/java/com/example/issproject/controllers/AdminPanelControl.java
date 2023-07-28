package com.example.issproject.controllers;

import com.example.issproject.models.AdminsEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class AdminPanelControl {
    @FXML
    Button backButton;

    AdminsEntity admin;
    public void setCurrentAdmin(AdminsEntity admin) {
        this.admin = admin;
    }

    @FXML
    public void backPressed() {
        try {

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
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(scene);
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}
