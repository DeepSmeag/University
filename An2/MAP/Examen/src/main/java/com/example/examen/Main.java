package com.example.examen;

import com.example.examen.config.Config;
import com.example.examen.controllers.loginPageController;
import com.example.examen.service.ServiceAll;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("loginPage.fxml"));
        loginPageController loginController = new loginPageController();
        loginController.setService(ServiceAll.getInstance());
        fxmlLoader.setController(loginController);
        Scene scene = new Scene(fxmlLoader.load(), Config.windowWidth/2, Config.windowHeight/2);
        stage.setTitle("Login");
        stage.setScene(scene);
//        getParameters().getRaw()
        stage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
