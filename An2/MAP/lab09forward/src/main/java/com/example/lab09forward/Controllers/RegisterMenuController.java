package com.example.lab09forward.Controllers;

import com.example.lab09forward.Main;
import com.example.lab09forward.config.Config;
import com.example.lab09forward.domain.Entities.User;
import com.example.lab09forward.domain.exceptions.RepoException;
import com.example.lab09forward.domain.exceptions.ServiceException;
import com.example.lab09forward.domain.exceptions.ValidationException;
import com.example.lab09forward.service.NetworkService;
import com.example.lab09forward.service.users.ServiceUsers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class RegisterMenuController {
    @FXML
    TextField firstNameField;
    @FXML
    TextField lastNameField;
    @FXML
    Button cancelRegistrationButton;
    @FXML
    Button registerButton;
    @FXML
    Text errorText;
    private ServiceUsers serviceUsers;
    @FXML
    public void initialize() {
        errorText.setTextAlignment(TextAlignment.CENTER);
        errorText.setText("");
        errorText.setOpacity(0.0);
    }
    @FXML
    public void registerUser() {
        errorText.setOpacity(0.0);
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        try {
            serviceUsers.addUser(firstName, lastName);
            errorText.setText("User registered successfully!");
            errorText.setOpacity(1.0);
            errorText.setFill(Paint.valueOf("green"));

        }
        catch(ServiceException e){
            errorText.setFill(Paint.valueOf("red"));
            errorText.setText(e.getMessage());
            errorText.setOpacity(1.0);
        }
        catch(ValidationException e) {
            errorText.setFill(Paint.valueOf("red"));
            errorText.setText("Input is invalid");
            errorText.setOpacity(1.0);
        }
    }
    @FXML
    public void cancelRegistration() {
        firstNameField.setText("");
        lastNameField.setText("");
        errorText.setOpacity(0.0);
        errorText.setText("");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("loginMenu.fxml"));
            Scene loginMenuScene = new Scene(fxmlLoader.load(), Config.loginMenuWidth, Config.loginMenuHeight);
            LoginMenuController loginMenuController = fxmlLoader.getController();
            loginMenuController.setService(ServiceUsers.getInstance());

            Stage stage = (Stage) cancelRegistrationButton.getScene().getWindow();
            stage.setTitle("Login");
            stage.setScene(loginMenuScene);
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Fail");
            alert.setContentText("Could not cancel registration");
            alert.show();
            e.printStackTrace();
        }
    }
    public void setService(ServiceUsers instance) {
        this.serviceUsers = instance;
    }
}
