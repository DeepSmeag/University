package com.example.examen;

import com.example.examen.config.Config;
import com.example.examen.controllers.clientPageController;
import com.example.examen.controllers.mainPageController;
import com.example.examen.service.ServiceAll;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

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
        stage.show();


        List<String> clients = getParameters().getRaw();
        for (String client : clients) {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("clientPage.fxml"));

            clientPageController controller = new clientPageController();
            controller.setService(ServiceAll.getInstance());
            controller.setClient(client);
            fxmlLoader.setController(controller);
            Scene cScene = new Scene(loader.load(), Config.windowWidth, Config.windowHeight);
            Stage cStage = new Stage();
            cStage.setTitle(client);
            cStage.setScene(cScene);
            cStage.show();
        }
    }

    public static void main(String[] args) {
        args = new String[]{"1", "2"};
        launch(args);
    }
}
