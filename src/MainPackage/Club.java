package MainPackage;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Club implements Serializable {
    private String Name;
    private double TotalWeeklySalary;
    private List<Player> playerList=new ArrayList<>();
    public void AddPlayer(Player P){
        playerList.add(P);
    }

    public Club(){

    }

    public Club(String Name,Double TotalWeeklySalary){
        this.Name=Name;
        this.TotalWeeklySalary=TotalWeeklySalary;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String Name) {
        this.Name =Name;
    }

    public void setTotalWeeklySalaryOfClub(Double TotalSalary){
        this.TotalWeeklySalary=TotalSalary;
    }

    public double getTotalWeeklySalary(){
        return TotalWeeklySalary*52;
    }

    public List<Player>getPlayerList(){
        return playerList;
    }
    public int getPlayerCount(){
        return playerList.size();
    }
}