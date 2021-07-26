package MainPackage;

import java.io.Serializable;

public class Player implements Serializable{
    private String Name;
    private String Country;
    private int Age;
    private double Height;
    private String ClubName;
    private String Position;
    private int Number;
    private double WeeklySalary;
    private boolean OnSell;

    public Player() {

    }

    public Player(String Name,String Country,Integer Age,Double Height,String ClubName,String Position,Integer Number,Double WeeklySalary,boolean OnSell){
        this.Name=(Name);
        this.Country=(Country);
        this.Age=(Age);
        this.Height=(Height);
        this.ClubName=(ClubName);
        this.Position=(Position);
        this.Number=(Number);
        this.WeeklySalary=(WeeklySalary);
        this.OnSell=(OnSell);
    }

    public String getName() {
        return Name;
    }


    public String getCountry() {
        return Country;
    }


    public int getAge() {
        return Age;
    }


    public double getHeight() {
        return Height;
    }


    public double getWeeklySalary() {
        return WeeklySalary;
    }


    public String getClubName() {
        return ClubName;
    }

    public void setClubName(String ClubName){
        this.ClubName=ClubName;
    }

    public String getPosition() {
        return Position;
    }


    public int getNumber() {
        return Number;
    }

    public void setOnSell(boolean OnSell){ this.OnSell=OnSell;}
    public boolean getOnSell(){ return this.OnSell; }


}