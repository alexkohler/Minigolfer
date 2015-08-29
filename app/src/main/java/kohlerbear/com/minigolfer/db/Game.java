package kohlerbear.com.minigolfer.db;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by alex on 8/20/15.
 */
public class Game implements Serializable {


    //Game fields
    private long gameID;
    private long date;
    private String location;
    private ArrayList<Player> players;

    public long getGameID() {
        return gameID;
    }

    public void setGameID(long gameID) {
        this.gameID = gameID;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date= date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

}
