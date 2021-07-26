package MainPackage;

import ThreadClass.ClientHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Server {

    private static final String INPUT_FILE_NAME = "players.txt";
    private static final String OUTPUT_FILE_NAME = "players.txt";
    private static List<Club> AllClubList = new ArrayList<>();
    private static List<Player> AllPlayerList = new ArrayList<>();
    private static List<Player> PlayerToBeSold = new ArrayList<>();

    private ServerSocket serverSocket;

    public Server() throws IOException, ClassNotFoundException {
        serverSocket = new ServerSocket(33333);
        while (true) {
            Socket clubClientSocket = serverSocket.accept();
            ObjectInputStream ois = new ObjectInputStream(clubClientSocket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(clubClientSocket.getOutputStream());
            //sending each client to a individual thread
            Thread t = new ClientHandler(clubClientSocket, ois, oos);
            t.start();
        }
    }

    public static void main(String args[]) throws Exception {
        ReadFromFile();
        InitializeClub();
        Server server = new Server();
    }

    public static void ReadFromFile() throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        while (true) {
            String line = br.readLine();
            if (line == null) break;
            String[] tokens = line.split(",");
            Player temp = new Player(tokens[0], tokens[1], Integer.parseInt(tokens[2]), Double.parseDouble(tokens[3]),
                    tokens[4], tokens[5], Integer.parseInt(tokens[6]), Double.parseDouble(tokens[7]),Boolean.parseBoolean(tokens[8]));

            AllPlayerList.add(temp);
            if(Boolean.parseBoolean(tokens[8])==true){
                PlayerToBeSold.add(temp);
            }
        }
    }

    public static void writeToFile(List<Player> PlayerList) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
        for (Player P : PlayerList) {
            bw.write(P.getName()+","+P.getCountry()+","+P.getAge()+","+P.getHeight()+","+P.getClubName()+","+P.getPosition()+","+P.getNumber()+","+P.getWeeklySalary()+","+P.getOnSell());
            bw.write("\n");
        }
        bw.close();
    }

    public static void InitializeClub() throws Exception {

//        This part creates a new Club object and adds it to the AllClubList in case when the
//        club does not exit in the list

        for (Player P : AllPlayerList) {
            boolean found = false;
            for (Club C : AllClubList) {
                if (C.getName().equalsIgnoreCase(P.getClubName())) {
                    found = true;
                    break;
                }
            }
            if (found == false) {
                Club temp = new Club(P.getClubName(), 0.0);
                //temp.setName(P.getClubName());
                AllClubList.add(temp);
            }
        }

        //Adding Players to their respective club
        for (Club C : AllClubList) {
            for (Player P : AllPlayerList) {
                if (P.getClubName().equalsIgnoreCase(C.getName()) && !P.getOnSell()) {
                    C.AddPlayer(P);
                }
            }
        }

        //setting club weekly salary
        for (Club C : AllClubList) {
            double TotalSalary = 0;
            for (Player P : AllPlayerList) {
                if (P.getClubName().equalsIgnoreCase(C.getName()) && !P.getOnSell()) {
                    TotalSalary += P.getWeeklySalary();
                }
            }

            C.setTotalWeeklySalaryOfClub(Double.parseDouble(Double.toString(TotalSalary)));
        }
    }

    public static List<Player> getPlayerToBeSold() {
        return PlayerToBeSold;
    }

    public static List<Club> getAllClubList() {
        return AllClubList;
    }
    public static List<Player>getAllPlayerList(){
        return AllPlayerList;
    }
}

