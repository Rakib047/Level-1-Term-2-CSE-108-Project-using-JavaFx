package ControllerClasses;

import MainPackage.Club;
import MainPackage.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;


public class MenuController {
    private static List<Player> myClubPlayerList;
    private static Socket socket;
    private static ObjectOutputStream oos;
    private Club myClub;
    private ObjectInputStream ois;

    @FXML
    private Button loginButton;
    @FXML
    private Button MainMenuSearchButton;
    @FXML
    private Button ClubManagementButton;
    @FXML
    private Button ShowClubButton;
    @FXML
    private Button ShowClubBackButton;
    @FXML
    private Button PlayerNameButton;
    @FXML
    private Button CountryNameButton;
    @FXML
    private Button PositionNameButton;
    @FXML
    private Button SalaryRangeButton;
    @FXML
    private Button CountrywisePlayerButton;
    @FXML
    private Button MaximumSalaryButton;
    @FXML
    private Button MaximumAgeButton;
    @FXML
    private Button MaximumHeightButton;
    @FXML
    private Button ClubYearlySalaryButton;
    @FXML
    private Button ExitButton;
    @FXML
    private Button SearchMenuBackButton;

    @FXML
    private TextField clientNameTextField;

    public static List<Player> getMyClubPlayerList() {
        return myClubPlayerList;
    }


    public void clientConnction(ActionEvent event) throws Exception {
        socket = new Socket("127.0.0.1", 33333);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());


        oos.writeObject(clientNameTextField.getText());
        myClub = (Club) ois.readObject();
        myClubPlayerList = myClub.getPlayerList();

        Parent root = FXMLLoader.load(getClass().getResource("../FXMLFiles/MainMenu.fxml"));
        Stage window = (Stage) loginButton.getScene().getWindow();
        window.setTitle("User: "+myClub.getName());
        window.setScene(new Scene(root));
    }

    public void setExitButton(ActionEvent event){
        System.exit(0);
    }

    //showClub list button actio
    public void setShowClubButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../FXMLFiles/showClub.fxml"));
        Stage window = (Stage) ShowClubButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void setShowClubBackButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../FXMLFiles/LoginMenu.fxml"));
        Stage window = (Stage) ShowClubBackButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    //Main menu button action events
    public void setMainMenuSearchButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../FXMLFiles/SearchMenu.fxml"));
        Stage window = (Stage) MainMenuSearchButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    //Club management button action events
    public void setClubManagementButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../FXMLFiles/BuySellMenu.fxml"));
        Stage window = (Stage) ClubManagementButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    //Search menu button action events
    public void setPlayerNameButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../FXMLFiles/PlayerName.fxml"));
        Stage window = (Stage) PlayerNameButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void setCountryNameButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../FXMLFiles/CountryName.fxml"));
        Stage window = (Stage) PlayerNameButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void setPositionNameButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../FXMLFiles/PositionName.fxml"));
        Stage window = (Stage) PlayerNameButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void setSalaryRangeButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../FXMLFiles/SalaryRange.fxml"));
        Stage window = (Stage) PlayerNameButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void setCountrywisePlayerButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../FXMLFiles/CountrywisePlayer.fxml"));
        Stage window = (Stage) PlayerNameButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void setMaximumSalaryButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../FXMLFiles/MaximumSalary.fxml"));
        Stage window = (Stage) PlayerNameButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void setMaximumAgeButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../FXMLFiles/MaximumAge.fxml"));
        Stage window = (Stage) PlayerNameButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void setMaximumHeightButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../FXMLFiles/MaximumHeight.fxml"));
        Stage window = (Stage) PlayerNameButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void setClubYearlySalaryButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../FXMLFiles/ClubYearlySalary.fxml"));
        Stage window = (Stage) PlayerNameButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void setSearchMenuBackButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../FXMLFiles/MainMenu.fxml"));
        Stage window = (Stage) SearchMenuBackButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

}
