package ThreadClass;

import MainPackage.Club;
import MainPackage.Player;
import MainPackage.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

public class ClientHandler extends Thread {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public ClientHandler(Socket socket, ObjectInputStream ois, ObjectOutputStream oos) {
        this.socket = socket;
        this.ois = ois;
        this.oos = oos;
    }

    @Override
    public void run() {
        Object obj = null;
        try {
            obj = ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (obj instanceof String) {
            String str = (String) obj;
            //server sending the club information when a club logins
            for (Club C : Server.getAllClubList()) {
                if (C.getName().equalsIgnoreCase(str)) {
                    try {
                        oos.writeObject(C);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            //when client wants to see the list of available players to buy
            if (str.equalsIgnoreCase("showBuyPlayerList")) {
                try {
                    oos.writeObject(Server.getPlayerToBeSold());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        //selling process
        else if (obj instanceof Player) {
            Player soldPlayer = (Player) obj;
            //removing the sold player from the club that sold it globally
            for (Club C : Server.getAllClubList()) {
                if (soldPlayer.getClubName().equalsIgnoreCase(C.getName())) {
                    C.getPlayerList().remove(soldPlayer);
                }
            }
            Server.getPlayerToBeSold().add(soldPlayer);

            //updating info in the AllPlayer list so that it can be written to file
            for (Player P : Server.getAllPlayerList()) {
                if (P.getName().equalsIgnoreCase(soldPlayer.getName())) {
                    P.setOnSell(true);
                }
            }

            try {
                Server.writeToFile(Server.getAllPlayerList());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //buying process
        else if (obj instanceof HashMap) {
            //adding bought player to the buyer club globally and removing it from player to be sold
            HashMap<Player, String> map = (HashMap<Player, String>) obj;

            for (Player boughtPlayer : map.keySet()) {

                //if player is available to be bought
                for (Club C : Server.getAllClubList()) {
                    if (boughtPlayer.getClubName().equalsIgnoreCase(C.getName())) {
                        C.getPlayerList().add(boughtPlayer);
                    }
                }

                //removing the bought player from the selling player list
                Iterator itr = Server.getPlayerToBeSold().iterator();
                while (itr.hasNext()) {
                    Player P = (Player) itr.next();
                    if (P.getName().equalsIgnoreCase(boughtPlayer.getName()))
                        itr.remove();
                }

                //writing updated details to files
                for (Player P : Server.getAllPlayerList()) {
                    if (P.getName().equalsIgnoreCase(boughtPlayer.getName())) {
                        P.setOnSell(false);
                        P.setClubName(boughtPlayer.getClubName());
                    }
                }
                try {
                    Server.writeToFile(Server.getAllPlayerList());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            try {
                this.ois.close();
                this.oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
