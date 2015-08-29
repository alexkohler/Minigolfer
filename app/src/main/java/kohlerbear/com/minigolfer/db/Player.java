package kohlerbear.com.minigolfer.db;

import java.io.Serializable;

/**
 * Created by alex on 8/18/15.
 */
public class Player implements Serializable {

    public static final String TAG = "Player";
    private static final long serialVersionUID = -7406082437623008161L;


    private long playerId;
    private String playerName;
    private String golfballColor;
    private int hole1score;
    private int hole2score;
    private int hole3score;
    private int hole4score;
    private int hole5score;
    private int hole6score;
    private int hole7score;
    private int hole8score;
    private int hole9score;
    private int hole10score;
    private int hole11score;
    private int hole12score;
    private int hole13score;
    private int hole14score;
    private int hole15score;
    private int hole16score;
    private int hole17score;
    private int hole18score;

    public Player() {}

//TODO do we need a big constructor? for testing yes, but remove this later
    public Player(String playerName, /*String color,*/ int hole1scole) {
        this.playerName = playerName;
//        this.golfballColor = color;
        this.hole1score = hole1scole;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getGolfballColor() {
        return golfballColor;
    }

    public void setGolfballColor(String golfballColor) {
        this.golfballColor = golfballColor;
    }

    public int getHole1score() {
        return hole1score;
    }

    public void setHole1score(int hole1score) {
        this.hole1score = hole1score;
    }

    public int getHole2score() {
        return hole2score;
    }

    public void setHole2score(int hole2score) {
        this.hole2score = hole2score;
    }

    public int getHole3score() {
        return hole3score;
    }

    public void setHole3score(int hole3score) {
        this.hole3score = hole3score;
    }

    public int getHole4score() {
        return hole4score;
    }

    public void setHole4score(int hole4score) {
        this.hole4score = hole4score;
    }

    public int getHole5score() {
        return hole5score;
    }

    public void setHole5score(int hole5score) {
        this.hole5score = hole5score;
    }

    public int getHole6score() {
        return hole6score;
    }

    public void setHole6score(int hole6score) {
        this.hole6score = hole6score;
    }

    public int getHole7score() {
        return hole7score;
    }

    public void setHole7score(int hole7score) {
        this.hole7score = hole7score;
    }

    public int getHole8score() {
        return hole8score;
    }

    public void setHole8score(int hole8score) {
        this.hole8score = hole8score;
    }

    public int getHole9score() {
        return hole9score;
    }

    public void setHole9score(int hole9score) {
        this.hole9score = hole9score;
    }

    public int getHole10score() {
        return hole10score;
    }

    public void setHole10score(int hole10score) {
        this.hole10score = hole10score;
    }

    public int getHole11score() {
        return hole11score;
    }

    public void setHole11score(int hole11score) {
        this.hole11score = hole11score;
    }

    public int getHole12score() {
        return hole12score;
    }

    public void setHole12score(int hole12score) {
        this.hole12score = hole12score;
    }

    public int getHole13score() {
        return hole13score;
    }

    public void setHole13score(int hole13score) {
        this.hole13score = hole13score;
    }

    public int getHole14score() {
        return hole14score;
    }

    public void setHole14score(int hole14score) {
        this.hole14score = hole14score;
    }

    public int getHole15score() {
        return hole15score;
    }

    public void setHole15score(int hole15score) {
        this.hole15score = hole15score;
    }

    public int getHole16score() {
        return hole16score;
    }

    public void setHole16score(int hole16score) {
        this.hole16score = hole16score;
    }

    public int getHole17score() {
        return hole17score;
    }

    public void setHole17score(int hole17score) {
        this.hole17score = hole17score;
    }

    public int getHole18score() {
        return hole18score;
    }

    public void setHole18score(int hole18score) {
        this.hole18score = hole18score;
    }


}
