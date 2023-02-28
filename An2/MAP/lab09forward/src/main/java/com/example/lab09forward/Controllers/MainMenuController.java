package com.example.lab09forward.Controllers;

import com.example.lab09forward.Main;
import com.example.lab09forward.config.Config;
import com.example.lab09forward.domain.Entities.User;
import com.example.lab09forward.service.NetworkService;
import com.example.lab09forward.service.friendships.ServiceFriendships;
import com.example.lab09forward.service.messages.ServiceMessages;
import com.example.lab09forward.service.users.ServiceUsers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class MainMenuController {
    //    -----------TEXT
    @FXML
    Text loggedUserName;
    //    -----------BUTTONS
    @FXML
    Button logoutButton;
    @FXML
    Button friendsPageButton;
    @FXML
    Button messagesPageButton;
    //    -----------MISC
    @FXML
    Pane subPage;
    @FXML
    BorderPane menuPane;
    NetworkService networkService;
    private User loggedUser;

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;

    }

    public void getSubPage(String pageName, AbstractSubMenuController controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(pageName + "Page.fxml"));
            controller.setLoggedUser(loggedUser);
            controller.setServices(ServiceUsers.getInstance(), ServiceFriendships.getInstance(), ServiceMessages.getInstance());
            fxmlLoader.setController(controller);
            subPage = fxmlLoader.load();
            subPage.setPrefHeight(Config.pagePaneHeight);
            subPage.setPrefWidth(Config.pagePaneWidth);
            menuPane.setCenter(subPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        // Default page should be "Friends"
        loggedUserName.setText(loggedUser.getFirstName() + " " + loggedUser.getLastName());
        button1Friends();
    }

    public void setService(NetworkService instance) {
        this.networkService = instance;
    }

    @FXML
    public void executeLogout() {
        loggedUserName.setText("");
        loggedUser = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("loginMenu.fxml"));
            Scene loginMenuScene = new Scene(fxmlLoader.load(), Config.loginMenuWidth, Config.loginMenuHeight);
            LoginMenuController loginMenuController = fxmlLoader.getController();
            loginMenuController.setService(ServiceUsers.getInstance());

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setTitle("Login");
            stage.setScene(loginMenuScene);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Fail");
            alert.setContentText("Could not log out");
            alert.show();
            e.printStackTrace();
        }
    }

    @FXML
    public void button1Friends() {
        getSubPage("friends", new FriendsMenuController());
    }

    @FXML
    public void button2Messages() {
        getSubPage("messages", new MessagesMenuController());
        menuPane.setCenter(subPage);
    }

}
