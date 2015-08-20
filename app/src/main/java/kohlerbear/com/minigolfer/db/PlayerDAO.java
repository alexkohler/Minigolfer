package kohlerbear.com.minigolfer.db;

/**
 * Created by alex on 8/18/15.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PlayerDAO {

    public static final String TAG = "EmployeeDAO";

    private Context mContext;

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private String[] mAllColumns = { DBHelper.COLUMN_PLAYER_ID, DBHelper.COLUMN_PLAYER_NAME,
            DBHelper.COLUMN_GOLFBALL_COLOR, DBHelper.COLUMN_HOLE_1_SCORE, DBHelper.COLUMN_HOLE_2_SCORE,
            DBHelper.COLUMN_HOLE_3_SCORE,DBHelper.COLUMN_HOLE_4_SCORE, DBHelper.COLUMN_HOLE_5_SCORE,
            DBHelper.COLUMN_HOLE_6_SCORE,DBHelper.COLUMN_HOLE_7_SCORE, DBHelper.COLUMN_HOLE_8_SCORE,
            DBHelper.COLUMN_HOLE_9_SCORE,DBHelper.COLUMN_HOLE_10_SCORE, DBHelper.COLUMN_HOLE_11_SCORE,
            DBHelper.COLUMN_HOLE_12_SCORE,DBHelper.COLUMN_HOLE_13_SCORE, DBHelper.COLUMN_HOLE_14_SCORE,
            DBHelper.COLUMN_HOLE_15_SCORE,DBHelper.COLUMN_HOLE_16_SCORE, DBHelper.COLUMN_HOLE_17_SCORE,
            DBHelper.COLUMN_HOLE_18_SCORE
    };

    //blank string is for zero placeholder so we can maintain more human indexing
    private String[] mHoleColumns = { "", DBHelper.COLUMN_HOLE_1_SCORE, DBHelper.COLUMN_HOLE_2_SCORE,
            DBHelper.COLUMN_HOLE_3_SCORE,DBHelper.COLUMN_HOLE_4_SCORE, DBHelper.COLUMN_HOLE_5_SCORE,
            DBHelper.COLUMN_HOLE_6_SCORE,DBHelper.COLUMN_HOLE_7_SCORE, DBHelper.COLUMN_HOLE_8_SCORE,
            DBHelper.COLUMN_HOLE_9_SCORE,DBHelper.COLUMN_HOLE_10_SCORE, DBHelper.COLUMN_HOLE_11_SCORE,
            DBHelper.COLUMN_HOLE_12_SCORE,DBHelper.COLUMN_HOLE_13_SCORE, DBHelper.COLUMN_HOLE_14_SCORE,
            DBHelper.COLUMN_HOLE_15_SCORE,DBHelper.COLUMN_HOLE_16_SCORE, DBHelper.COLUMN_HOLE_17_SCORE,
            DBHelper.COLUMN_HOLE_18_SCORE
    };

    public PlayerDAO(Context context) {
        mDbHelper = new DBHelper(context);
        this.mContext = context;
        // open the database
        try {
            open();
        }
        catch(SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public void insertPlayer(String playerName, String color, int hole1score, int hole2score, int hole3score, int hole4score, int hole5score,
                               int hole6score, int hole7score, int hole8score, int hole9score, int hole10score, int hole11score, int hole12score,
                               int hole13score, int hole14score, int hole15score, int hole16score, int hole17score, int hole18score) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_PLAYER_NAME, playerName);
        values.put(DBHelper.COLUMN_GOLFBALL_COLOR, color);
        //TODO do I need to add a null check?
        values.put(DBHelper.COLUMN_HOLE_1_SCORE, hole1score);
        values.put(DBHelper.COLUMN_HOLE_2_SCORE, hole2score);
        values.put(DBHelper.COLUMN_HOLE_3_SCORE, hole3score);
        values.put(DBHelper.COLUMN_HOLE_4_SCORE, hole4score);
        values.put(DBHelper.COLUMN_HOLE_5_SCORE, hole5score);
        values.put(DBHelper.COLUMN_HOLE_6_SCORE, hole6score);
        values.put(DBHelper.COLUMN_HOLE_7_SCORE, hole7score);
        values.put(DBHelper.COLUMN_HOLE_8_SCORE, hole8score);
        values.put(DBHelper.COLUMN_HOLE_9_SCORE, hole9score);
        values.put(DBHelper.COLUMN_HOLE_10_SCORE, hole10score);
        values.put(DBHelper.COLUMN_HOLE_11_SCORE, hole11score);
        values.put(DBHelper.COLUMN_HOLE_12_SCORE, hole12score);
        values.put(DBHelper.COLUMN_HOLE_13_SCORE, hole13score);
        values.put(DBHelper.COLUMN_HOLE_14_SCORE, hole14score);
        values.put(DBHelper.COLUMN_HOLE_15_SCORE, hole15score);
        values.put(DBHelper.COLUMN_HOLE_16_SCORE, hole16score);
        values.put(DBHelper.COLUMN_HOLE_17_SCORE, hole17score);
        values.put(DBHelper.COLUMN_HOLE_18_SCORE, hole18score);

//        long insertId = mDatabase.insert(DBHelper.TABLE_CURRENT_GAME, null, values);
//        Cursor cursor = mDatabase.query(DBHelper.TABLE_CURRENT_GAME,
//                mAllColumns, DBHelper.COLUMN_PLAYER_ID + " = " + insertId, null, null, null, null);
//        cursor.moveToFirst();
//        Player newPlayer = cursorToPlayer(cursor);
//        cursor.close();
//        return newPlayer;
    }

    //TODO golf ball color stuff?
    public void insertPlayer(String playerName) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_PLAYER_NAME, playerName);

        long insertId = mDatabase.insert(DBHelper.TABLE_CURRENT_GAME, null, values);
//        Cursor cursor = mDatabase.query(DBHelper.TABLE_CURRENT_GAME,
//                mAllColumns, DBHelper.COLUMN_PLAYER_ID + " = " + insertId, null, null, null, null);
//        cursor.moveToFirst();
//        Player newPlayer = cursorToPlayer(cursor);
//        cursor.close();
//        return newPlayer;
    }

    public void updateHoleValue(String playerName, int holeNumber, int stroke) {
        ContentValues values = new ContentValues();
        values.put(mHoleColumns[holeNumber], stroke);
        mDatabase.update(DBHelper.TABLE_CURRENT_GAME, values, DBHelper.COLUMN_PLAYER_NAME + "=?", new String[]{playerName});//table values whereclause whereargs
    }

    public void updatePlayerName(long playerID, String newName) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_PLAYER_NAME, newName);
        mDatabase.update(DBHelper.TABLE_CURRENT_GAME, values, DBHelper.COLUMN_PLAYER_ID + "=?", new String[]{String.valueOf(playerID)});//table values whereclause whereargs
    }


    public void deletePlayer(String playerName) {
        mDatabase.delete(DBHelper.TABLE_CURRENT_GAME, DBHelper.COLUMN_PLAYER_NAME + "=?", new String[]{playerName});
    }

    public List<Player> getAllPlayers() {
        List<Player> listPlayers = new ArrayList<Player>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_CURRENT_GAME,
                mAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Player player = cursorToPlayer(cursor);
            listPlayers.add(player);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listPlayers;
    }

    public boolean playerExists (String playerName) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_CURRENT_GAME,
                mAllColumns, DBHelper.COLUMN_PLAYER_NAME + " = '" + playerName + "'", null, null, null, null);
        return !(cursor.getCount() == 0); //if getCount is zero, then the player DOESN'T exist
    }

    //TODO additional methods needed?
    //TODO do you need null checks here? or is this handled at db levell?
    private Player cursorToPlayer(Cursor cursor) {
        Player player = new Player();
        player.setPlayerId(cursor.getLong(0));
        player.setPlayerName(cursor.getString(1));
        player.setGolfballColor(cursor.getString(2));
        player.setHole1score(cursor.getInt(3));
        player.setHole2score(cursor.getInt(4));
        player.setHole3score(cursor.getInt(5));
        player.setHole4score(cursor.getInt(6));
        player.setHole5score(cursor.getInt(7));
        player.setHole6score(cursor.getInt(8));
        player.setHole7score(cursor.getInt(9));
        player.setHole8score(cursor.getInt(10));
        player.setHole9score(cursor.getInt(11));
        player.setHole10score(cursor.getInt(12));
        player.setHole11score(cursor.getInt(13));
        player.setHole12score(cursor.getInt(14));
        player.setHole13score(cursor.getInt(15));
        player.setHole14score(cursor.getInt(16));
        player.setHole15score(cursor.getInt(17));
        player.setHole16score(cursor.getInt(18));
        player.setHole17score(cursor.getInt(19));
        player.setHole18score(cursor.getInt(20));

        return player;
    }

}
