package MainPackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClubClient extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("../FXMLFiles/LoginMenu.fxml"));
        primaryStage.setTitle("Football Manager");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }



    public static void main(String[] args) throws IOException, ClassNotFoundException {
        launch(args);
    }


}
