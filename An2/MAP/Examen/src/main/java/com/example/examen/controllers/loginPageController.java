package com.example.examen.controllers;

import com.example.examen.Main;
import com.example.examen.config.Config;
import com.example.examen.domain.entities.Client;
import com.example.examen.service.ServiceAll;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class loginPageController {
    private ServiceAll serviceAll;
    List<clientPageController> pages = new ArrayList<>();
    @FXML
    TextField userField;
    @FXML
    Button loginButton;

    public void initialize() {

    }

    public void setService(ServiceAll serviceAll) {
        this.serviceAll = serviceAll;
    }


    public void login() {
        String username = userField.getText();
        Client client = serviceAll.findClient(username);
        if (client == null) {
            showErrorAlert("Err", "Wrong username");
            return;
        }
        // create window for user
        try {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("clientPage.fxml"));
        clientPageController clientController = new clientPageController();
        pages.add(clientController);
        clientController.setService(serviceAll, client, pages);
        loader.setController(clientController);
        Scene scene = new Scene(loader.load(), Config.windowWidth, Config.windowHeight);
        Stage stage = new Stage();
        stage.setTitle(client.getName());
        stage.setScene(scene);
        stage.show();

        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        alert.showAndWait();
    }
    public void refreshUsers() {
        for(clientPageController clientController: pages) {
            clientController.refreshTable();
        }
    }
}
