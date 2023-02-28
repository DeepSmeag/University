package com.example.lab09forward;

import com.example.lab09forward.Controllers.LoginMenuController;
import com.example.lab09forward.config.Config;
import com.example.lab09forward.service.users.ServiceUsers;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), Config.loginMenuWidth, Config.loginMenuHeight);
        stage.setTitle("Login");
        LoginMenuController loginMenuController = fxmlLoader.getController();
        loginMenuController.setService(ServiceUsers.getInstance());
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}
