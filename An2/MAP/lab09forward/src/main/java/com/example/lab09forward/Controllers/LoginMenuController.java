package com.example.lab09forward.Controllers;

import com.example.lab09forward.Main;
import com.example.lab09forward.config.Config;
import com.example.lab09forward.domain.Entities.User;
import com.example.lab09forward.service.NetworkService;
import com.example.lab09forward.service.users.ServiceUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class LoginMenuController {
    ObservableList<User> modelUsers = FXCollections.observableArrayList();

    List<String> modelUsersNames;

    private ServiceUsers serviceUsers;
//    ----------FIELDS
    @FXML
    TextField firstNameField;
    @FXML
    TextField lastNameField;
    @FXML
    TextField passField;
//    ----------TEXT
    @FXML
    Text loginErrorText;
//    ----------BUTTONS
    @FXML
    Button loginButton;
    @FXML
    Button registerButton;
//    ----------TABLE
    @FXML
    TableColumn<User, String> tableColumnFirstName;
    @FXML
    TableColumn<User, String> tableColumnLastName;
    @FXML
    TableView<User> tableViewUsers;


//    ----------METHODS
    @FXML
    public void loginButtonPressed(){
        try{
            User user = serviceUsers.findUser(firstNameField.getText(), lastNameField.getText());
//            If we get here user exists, we can log in
//            Pass logged user to main menu
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainMenu.fxml"));
            MainMenuController mainMenuController = new MainMenuController();
            mainMenuController.setLoggedUser(user);
            mainMenuController.setService(NetworkService.getInstance());
            fxmlLoader.setController(mainMenuController);
            Scene mainMenuScene = new Scene(fxmlLoader.load(), Config.mainMenuWidth, Config.mainMenuHeight);


            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setTitle("Main Menu");
            stage.setScene(mainMenuScene);

        }
        catch(Exception e){
            loginErrorText.setOpacity(1.00);
            System.out.println(e.getMessage());
        }
    }
    @FXML
    public void registerButtonPressed(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("registerMenu.fxml"));
            Scene registerMenuScene = new Scene(fxmlLoader.load(), Config.registerMenuWidth, Config.registerMenuHeight);
            RegisterMenuController registerMenuController = fxmlLoader.getController();
            registerMenuController.setService(ServiceUsers.getInstance());


            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setTitle("Register");
            stage.setScene(registerMenuScene);

        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    @FXML
    public void initialize() {
        loginErrorText.setOpacity(0.0);
        tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));


        tableViewUsers.setItems(modelUsers);

        tableViewUsers.getSelectionModel().selectedItemProperty().addListener(o -> handleSelection());

    }

    private void handleSelection() {
        User selectedUser = tableViewUsers.getSelectionModel().getSelectedItem();
        firstNameField.setText(selectedUser.getFirstName());
        lastNameField.setText(selectedUser.getLastName());
    }


    public void setService(ServiceUsers serviceUsers) {
        this.serviceUsers = serviceUsers;
        List<User> users = StreamSupport.stream(serviceUsers.findAllUsers().spliterator(), false)
                .collect(Collectors.toList());
        modelUsers.setAll(users);

    }
}
