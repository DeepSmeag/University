package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.service.ServiceManager;

import java.io.IOException;

public class LoginController {
    @FXML
    TextField loginName;

    @FXML
    Button loginButton;

    @FXML
    void loginButtonClicked() throws IOException {
        System.out.println("Clicked!!!");

        System.out.println(loginName);

        String name = loginName.getText();
        if(loginName.getText()==null || loginName.getText().length()<3) {
            System.out.println("HERE?");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Hellow");
            alert.setHeaderText("Fail");
            alert.setContentText("Invalid user name");
            alert.show();
            return;
        }

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/notaView.fxml"));
        AnchorPane root=loader.load();

        NotaController ctrl=loader.getController();
        ctrl.setService(new ServiceManager());

        ctrl.welcomeUser(name);

        Stage stage = new Stage();
        stage.setScene(new Scene(root, 800, 600));
        //stage.setTitle("Hello, " + name);
        stage.show();

        Stage thisStage = (Stage) loginButton.getScene().getWindow();
        thisStage.close();


    }
}
