import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class TestSem9 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/loginPage.fxml"));
        AnchorPane root=loader.load();

        LoginController ctrl = loader.getController();


        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.setTitle("Login");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
