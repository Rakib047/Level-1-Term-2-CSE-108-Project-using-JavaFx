package ControllerClasses;

import MainPackage.Country;
import MainPackage.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SearchController {
    @FXML private TableView<Player> table;
    @FXML private TableColumn<Player, String> nameCol;
    @FXML private TableColumn<Player, String> countryCol;
    @FXML private TableColumn<Player, Integer> ageCol;
    @FXML private TableColumn<Player, Double> heightCol;
    @FXML private TableColumn<Player, String> clubCol;
    @FXML private TableColumn<Player, String> positionCol;
    @FXML private TableColumn<Player, Integer> jerseyCol;
    @FXML private TableColumn<Player, Double> salaryCol;

    @FXML private TableView<Country>coutrywiseTable;
    @FXML private TableColumn<Country, String> countryNameCol;
    @FXML private TableColumn<Country, Integer> PlayerCountCol;

    @FXML private Button PlayerSearchButton;
    @FXML private Button CountrySearchButton;
    @FXML private Button PositionSearchButton;
    @FXML private Button SalaryRangeSearchButton;
    @FXML private Button CountrywiseSearchButton;
    @FXML private Button MaxSalarySearchButton;
    @FXML private Button MaxAgeSearchButton;
    @FXML private Button MaxHeightSearchButton;
    @FXML private Button ClubYearlySearchButton;
    @FXML private Button BacktoSearchMenuButton;
    @FXML private Button CloseWindow;


    @FXML private TextField PlayerSearchTextField;
    @FXML private TextField CountrySearchTextField;
    @FXML private TextField PositionSearchTextField;
    @FXML private TextField SalaryRangeSearchLowerTextField;
    @FXML private TextField SalaryRangeSearchUpperTextField;
    @FXML private TextField CountrywiseSearchTextField;

    @FXML private Label clubNameLabel;
    @FXML private Label clubSalaryLabel;
    @FXML private Button showLabel;

    //backButton action
    public void setBacktoSearchMenuButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../FXMLFiles/SearchMenu.fxml"));
        Stage window = (Stage) BacktoSearchMenuButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    //searching
    public void setPlayerSearchButton(ActionEvent event){
        boolean found=false;
        List<Player>matchedList=new ArrayList<>();
        for(Player P:MenuController.getMyClubPlayerList()){
            if(P.getName().equalsIgnoreCase(PlayerSearchTextField.getText())
            ||PlayerSearchTextField.getText().equalsIgnoreCase("all")){
                found=true;
                matchedList.add(P);
            }
        }
        if(found){
           showPlayerInfo(matchedList);
        }
        else{
           showErrorWindow();
        }
    }

    public void setCountrySearchButton(ActionEvent event){
        boolean found=false;
        List<Player>matchedList=new ArrayList<>();
        for(Player P:MenuController.getMyClubPlayerList()){
            if(P.getCountry().equalsIgnoreCase(CountrySearchTextField.getText())
            ||CountrySearchTextField.getText().equalsIgnoreCase("all")){
                found=true;
                matchedList.add(P);
            }
        }
        if(found){
            showPlayerInfo(matchedList);
        }
        else{
           showErrorWindow();
        }
    }

    public void setPositionSearchButton(ActionEvent event){
        boolean found=false;
        List<Player>matchedList=new ArrayList<>();
        for(Player P:MenuController.getMyClubPlayerList()){
            if(P.getPosition().equalsIgnoreCase(PositionSearchTextField.getText())
            ||PositionSearchTextField.getText().equalsIgnoreCase("all")){
                found=true;
                matchedList.add(P);
            }
        }
        if(found){
            showPlayerInfo(matchedList);
        }
        else{
         showErrorWindow();
        }
    }

    public void setSalaryRangeSearchButton(ActionEvent event){
        boolean found=false;
        List<Player>matchedList=new ArrayList<>();
        for(Player P:MenuController.getMyClubPlayerList()){
            if(P.getWeeklySalary()>=Double.parseDouble(SalaryRangeSearchLowerTextField.getText()) && P.getWeeklySalary()<=Double.parseDouble(SalaryRangeSearchUpperTextField.getText())){
                found=true;
                matchedList.add(P);
            }
        }
        if(found){
            showPlayerInfo(matchedList);
        }
        else{
           showErrorWindow();
        }
    }

    public void setCountrywiseSearchButton(ActionEvent event){
        List<String> CountryList=new ArrayList<>();
        List<Country>countryObjList=new ArrayList<>();
        for(Player P:MenuController.getMyClubPlayerList()){
            if(!CountryList.contains(P.getCountry())){
                CountryList.add(P.getCountry());
            }
        }

        for(String country:CountryList){
            int count=0;
            for(Player P:MenuController.getMyClubPlayerList()){
                if(P.getCountry().equalsIgnoreCase(country)){
                    count++;
                }
            }
            countryObjList.add(new Country(country,count));
        }

        if(CountrywiseSearchTextField.getText().equalsIgnoreCase("ALL")||CountrywiseSearchTextField.getText().equalsIgnoreCase("ANY")){
            showCountryWiseInfo(countryObjList);
        }
        else{
            for(Country C:countryObjList){
                if(C.getCountryName().equalsIgnoreCase(CountrywiseSearchTextField.getText())){
                    List<Country>temp=new ArrayList<>();
                    temp.add(C);
                    showCountryWiseInfo(temp);
                    break;
                }
            }
        }
    }

    public void setMaxSalarySearchButton(ActionEvent event){
         double MaxSalary=0;
        List<Player>matchedList=new ArrayList<>();
         for(Player P:MenuController.getMyClubPlayerList()){
             if(P.getWeeklySalary()>MaxSalary){
                 MaxSalary=P.getWeeklySalary();
             }
         }

        for(Player P:MenuController.getMyClubPlayerList()){
            if(P.getWeeklySalary()==MaxSalary){
                matchedList.add(P);
            }
        }
         showPlayerInfo(matchedList);
    }

    public void setMaxAgeSearchButton(ActionEvent event){
        int MaxAge=0;
        List<Player>matchedList=new ArrayList<>();
        for(Player P:MenuController.getMyClubPlayerList()){
            if(P.getAge()>MaxAge){
                MaxAge=P.getAge();
            }
        }

        for(Player P:MenuController.getMyClubPlayerList()){
            if(P.getAge()==MaxAge){
                matchedList.add(P);
            }
        }
        showPlayerInfo(matchedList);
    }

    public void setMaxHeightSearchTextButton(ActionEvent event){
        double MaxHeight=0;
        List<Player>matchedList=new ArrayList<>();
        for(Player P:MenuController.getMyClubPlayerList()){
            if(P.getHeight()>MaxHeight){
                MaxHeight=P.getHeight();
            }
        }

        for(Player P:MenuController.getMyClubPlayerList()){
            if(P.getHeight()==MaxHeight){
                matchedList.add(P);
            }
        }
        showPlayerInfo(matchedList);
    }

    public void setClubYearlySalarySearchTextButton(ActionEvent event){
        int yearlySalary=0;
        String name=new String();
        for(Player P:MenuController.getMyClubPlayerList()){
            yearlySalary+=P.getWeeklySalary();
            name=P.getClubName();
        }
        yearlySalary*=52;
        clubNameLabel.setText(name);
        clubSalaryLabel.setText(String.valueOf(yearlySalary));
    }

    public void showPlayerInfo(List<Player>matchedPlayerList){

        ObservableList<Player> observablePlayerList= FXCollections.observableArrayList(matchedPlayerList );

        nameCol.setCellValueFactory(new PropertyValueFactory<Player,String>("Name"));
        countryCol.setCellValueFactory(new PropertyValueFactory<Player,String>("Country"));
        clubCol.setCellValueFactory(new PropertyValueFactory<Player,String>("ClubName"));
        ageCol.setCellValueFactory(new PropertyValueFactory<Player,Integer>("Age"));
        heightCol.setCellValueFactory(new PropertyValueFactory<Player,Double>("Height"));
        positionCol.setCellValueFactory(new PropertyValueFactory<Player,String>("Position"));
        jerseyCol.setCellValueFactory(new PropertyValueFactory<Player,Integer>("Number"));
        salaryCol.setCellValueFactory(new PropertyValueFactory<Player,Double>("WeeklySalary"));

        table.setItems(observablePlayerList);
    }

    public void showCountryWiseInfo(List<Country>countryObjList){
        ObservableList<Country> Countrylist= FXCollections.observableArrayList(countryObjList);
        countryNameCol.setCellValueFactory(new PropertyValueFactory<Country,String>("countryName"));
        PlayerCountCol.setCellValueFactory(new PropertyValueFactory<Country,Integer>("PlayerCount"));
        coutrywiseTable.setItems(Countrylist);
    }

    public void showErrorWindow(){

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../FXMLFiles/NotFound.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage window=new Stage();
        Scene scene=new Scene(root);
        window.setTitle("Error!");
        window.setScene(scene);
        window.showAndWait();
    }

    public void setCloseWindow(ActionEvent event){
        Stage window = (Stage) CloseWindow.getScene().getWindow();
        window.close();
    }

}
