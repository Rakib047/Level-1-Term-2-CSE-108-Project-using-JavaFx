package MainPackage;

public class Country {
    private String countryName;
    private int PlayerCount;

    public Country(String countryName,Integer playerCount){
        this.countryName=(countryName);
        this.PlayerCount=(playerCount);
    }

    public String getCountryName() {
        return countryName;
    }
    public int getPlayerCount() {
        return PlayerCount;
    }
}