package com.example.examen;

import com.example.examen.config.Config;
import com.example.examen.controllers.mainPageController;
import com.example.examen.service.ServiceAll;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainPage.fxml"));
//        mainPageController mainController = fxmlLoader.getController();
        mainPageController mainController = new mainPageController();
        mainController.setService(ServiceAll.getInstance());
        fxmlLoader.setController(mainController);
        Scene scene = new Scene(fxmlLoader.load(), Config.windowWidth, Config.windowHeight);
        stage.setTitle("Title");
        stage.setScene(scene);
//        getParameters().getRaw()
        stage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
