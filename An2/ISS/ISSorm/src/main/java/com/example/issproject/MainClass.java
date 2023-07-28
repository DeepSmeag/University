package com.example.issproject;

import com.example.issproject.controllers.MainControl;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainClass extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        initMainWindow(stage);

    }


    public void initMainWindow(Stage stage) throws IOException {
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

        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.OFF);
        launch();
    }
}
