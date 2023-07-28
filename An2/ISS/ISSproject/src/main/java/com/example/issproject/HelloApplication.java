package com.example.issproject;

import com.example.issproject.controllers.MainControl;
//import com.example.issproject.repository.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
       initMainWindow(stage);
//        Session session = HibernateUtil.getSessionFactory().openSession();

//        Configuration configuration = new Configuration();
//        configuration.configure("hibernate.cfg.xml");
//        SessionFactory sessionFactory = configuration.buildSessionFactory();

    }


    public void initMainWindow(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainMenu.fxml"));
        MainControl controller = new MainControl();
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load(), 426, 647);

        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}