package ControllerClasses;
import MainPackage.Player;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class BuySellController{

    @FXML private Button buyButton;
    @FXML private Button sellButton;
    @FXML private Button sellPlayerButton;
    @FXML private Button buyPlayerButton;
    @FXML private Button showPlayerListButton;
    @FXML private Button showBuyingPlayerListButton;
    @FXML private Button backSellMenuButton;
    @FXML private Button backBuySellMenuButton;
    @FXML private Button backBuyMenuButton;


    @FXML ListView<Player>availablePlayer;
    @FXML ListView<Player>buyablePlayer;


    public void setbuyButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../FXMLFiles/BuyPlayer.fxml"));
        Stage window = (Stage)buyButton .getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void setsellButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../FXMLFiles/SellPlayer.fxml"));
        Stage window = (Stage)sellButton .getScene().getWindow();
        window.setScene(new Scene(root));
    }

    //back buttons
    public void setbackSellMenuButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../FXMLFiles/BuySellMenu.fxml"));
        Stage window = (Stage)backSellMenuButton .getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void setbackBuyMenuButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../FXMLFiles/BuySellMenu.fxml"));
        Stage window = (Stage)backBuyMenuButton .getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void setbackBuySellMenuButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../FXMLFiles/MainMenu.fxml"));
        Stage window = (Stage)backBuySellMenuButton .getScene().getWindow();
        window.setScene(new Scene(root));
    }

    //showing buyable and own club player list to sell
    public void setshowPlayerListButton(ActionEvent event){
        availablePlayer.getItems().clear();
        availablePlayer.getItems().addAll(MenuController.getMyClubPlayerList());
        availablePlayer.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        availablePlayer.setCellFactory(param -> new ListCell<Player>() {
            @Override
            protected void updateItem(Player P, boolean empty) {
                super.updateItem(P, empty);

                if (empty || P == null || P.getName() == null) {
                    setText(null);
                } else {
                    setText(P.getName());
                }
            }
        });
    }


    public void setshowBuyingPlayerListButton(ActionEvent event) throws IOException, ClassNotFoundException {

            Socket socket = new Socket("127.0.0.1", 33333);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());


                oos.writeObject("showBuyPlayerList");
                Object obj = ois.readObject();
                List<Player> temp = (List<Player>) obj;


                //a club can't see its own player in the list who are to be sold
                String ownClubName = "";
                for (Player P : MenuController.getMyClubPlayerList()) {
                    ownClubName = P.getClubName();
                    break;
                }
                List<Player> playerToBeSold = new ArrayList<>();
                for (Player P : temp) {
                    if (!P.getClubName().equalsIgnoreCase(ownClubName)) playerToBeSold.add(P);
                }

                buyablePlayer.getItems().clear();
                buyablePlayer.getItems().addAll(playerToBeSold);
                buyablePlayer.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

                buyablePlayer.setCellFactory(param -> new ListCell<Player>() {
                    @Override
                    protected void updateItem(Player P, boolean empty) {
                        super.updateItem(P, empty);

                        if (empty || P == null || P.getName() == null) {
                            setText(null);
                        } else {
                            String str = P.getName() + "," + P.getClubName();
                            setText(str);
                        }
                    }
                });
    }

    //selling and buying process
    public void setsellPlayerButton(ActionEvent event) throws IOException {
        //sending the sold player to the server and removing from own club player list
        Socket socket=new Socket("127.0.0.1",33333);
        ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
        ObservableList<Player>players;
        players=availablePlayer.getSelectionModel().getSelectedItems();

        for(Player P:players){
            oos.writeObject(P);
            MenuController.getMyClubPlayerList().remove(P);
        }

        //showing updated player list after selling
        final int selectedIdx = availablePlayer.getSelectionModel().getSelectedIndex();
        if (selectedIdx != -1) {
            Player itemToRemove = availablePlayer.getSelectionModel().getSelectedItem();

            final int newSelectedIdx =
                    (selectedIdx == availablePlayer.getItems().size() - 1)
                            ? selectedIdx - 1
                            : selectedIdx;

            availablePlayer.getItems().remove(selectedIdx);
            availablePlayer.getSelectionModel().select(newSelectedIdx);
        }

    }

    public void setbuyPlayerButton(ActionEvent event) throws IOException, ClassNotFoundException {

        Socket socket=new Socket("127.0.0.1",33333);
        ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());

        ObservableList<Player>players;
        players=buyablePlayer.getSelectionModel().getSelectedItems();

        String buyerClubName = "";
        for(Player P:MenuController.getMyClubPlayerList()){
            buyerClubName=P.getClubName();
            break;
        }

        //adding player to the club local player list
        //sending the information to the server that a club has bought the player
        for(Player P:players){
            P.setClubName(buyerClubName);
            MenuController.getMyClubPlayerList().add(P);
            HashMap<Player,String>map=new HashMap<>();
            map.put(P,buyerClubName);
            oos.writeObject(map);
        }


        //showing updated player list after buying
        final int selectedIdx = buyablePlayer.getSelectionModel().getSelectedIndex();
        if (selectedIdx != -1) {
            Player itemToRemove = buyablePlayer.getSelectionModel().getSelectedItem();

            final int newSelectedIdx =
                    (selectedIdx == buyablePlayer.getItems().size() - 1)
                            ? selectedIdx - 1
                            : selectedIdx;

            buyablePlayer.getItems().remove(selectedIdx);
            buyablePlayer.getSelectionModel().select(newSelectedIdx);
        }
    }

}
