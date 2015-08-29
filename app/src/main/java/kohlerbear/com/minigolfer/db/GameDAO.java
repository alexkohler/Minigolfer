package kohlerbear.com.minigolfer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 8/20/15.
 */
public class GameDAO {
    public static final String TAG = "playerDAO";


    private String[] mAllColumns = {
            DBHelper.COLUMN_GAME_ID, DBHelper.COLUMN_GAME_DATE, DBHelper.COLUMN_GAME_LOC,
            DBHelper.COLUMN_PLAYER_ID,DBHelper.COLUMN_PLAYER_NAME,DBHelper.COLUMN_GOLFBALL_COLOR,
            DBHelper.COLUMN_HOLE_1_SCORE,DBHelper.COLUMN_HOLE_2_SCORE,DBHelper.COLUMN_HOLE_3_SCORE,
            DBHelper.COLUMN_HOLE_4_SCORE,DBHelper.COLUMN_HOLE_5_SCORE,DBHelper.COLUMN_HOLE_6_SCORE,
            DBHelper.COLUMN_HOLE_7_SCORE,DBHelper.COLUMN_HOLE_8_SCORE,DBHelper.COLUMN_HOLE_9_SCORE,
            DBHelper.COLUMN_HOLE_10_SCORE,DBHelper.COLUMN_HOLE_11_SCORE,DBHelper.COLUMN_HOLE_12_SCORE,
            DBHelper.COLUMN_HOLE_13_SCORE,DBHelper.COLUMN_HOLE_14_SCORE,DBHelper.COLUMN_HOLE_15_SCORE,
            DBHelper.COLUMN_HOLE_16_SCORE,DBHelper.COLUMN_HOLE_17_SCORE,DBHelper.COLUMN_HOLE_18_SCORE
    };

    private String[] mPlayerColumns = {
            DBHelper.COLUMN_PLAYER_ID, DBHelper.COLUMN_PLAYER_NAME, DBHelper.COLUMN_GOLFBALL_COLOR,
            DBHelper.COLUMN_HOLE_1_SCORE,DBHelper.COLUMN_HOLE_2_SCORE,DBHelper.COLUMN_HOLE_3_SCORE,
            DBHelper.COLUMN_HOLE_4_SCORE,DBHelper.COLUMN_HOLE_5_SCORE,DBHelper.COLUMN_HOLE_6_SCORE,
            DBHelper.COLUMN_HOLE_7_SCORE,DBHelper.COLUMN_HOLE_8_SCORE,DBHelper.COLUMN_HOLE_9_SCORE,
            DBHelper.COLUMN_HOLE_10_SCORE,DBHelper.COLUMN_HOLE_11_SCORE,DBHelper.COLUMN_HOLE_12_SCORE,
            DBHelper.COLUMN_HOLE_13_SCORE,DBHelper.COLUMN_HOLE_14_SCORE,DBHelper.COLUMN_HOLE_15_SCORE,
            DBHelper.COLUMN_HOLE_16_SCORE,DBHelper.COLUMN_HOLE_17_SCORE,DBHelper.COLUMN_HOLE_18_SCORE
    };


    private Context mContext;

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;


    public GameDAO(Context context) {
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

    /*
            DBHelper.COLUMN_PLAYER_ID,DBHelper.COLUMN_PLAYER_NAME,DBHelper.COLUMN_GOLFBALL_COLOR,
            DBHelper.COLUMN_HOLE_1_SCORE,DBHelper.COLUMN_HOLE_2_SCORE,DBHelper.COLUMN_HOLE_3_SCORE,
            DBHelper.COLUMN_HOLE_4_SCORE,DBHelper.COLUMN_HOLE_5_SCORE,DBHelper.COLUMN_HOLE_6_SCORE,
            DBHelper.COLUMN_HOLE_7_SCORE,DBHelper.COLUMN_HOLE_8_SCORE,DBHelper.COLUMN_HOLE_9_SCORE,
            DBHelper.COLUMN_HOLE_10_SCORE,DBHelper.COLUMN_HOLE_11_SCORE,DBHelper.COLUMN_HOLE_12_SCORE,
            DBHelper.COLUMN_HOLE_13_SCORE,DBHelper.COLUMN_HOLE_14_SCORE,DBHelper.COLUMN_HOLE_15_SCORE,
            DBHelper.COLUMN_HOLE_16_SCORE,DBHelper.COLUMN_HOLE_17_SCORE,DBHelper.COLUMN_HOLE_18_SCORE
     */

    public void insertGame(long gameUnixTime, String location, List<Player> players) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_GAME_DATE, gameUnixTime);
        values.put(DBHelper.COLUMN_GAME_LOC, location);
        for (Player player : players) {
            values.put(DBHelper.COLUMN_PLAYER_ID, player.getPlayerId());
            values.put(DBHelper.COLUMN_PLAYER_NAME, player.getPlayerName());
            values.put(DBHelper.COLUMN_GOLFBALL_COLOR, player.getGolfballColor());
            values.put(DBHelper.COLUMN_HOLE_1_SCORE, player.getHole1score());
            values.put(DBHelper.COLUMN_HOLE_2_SCORE, player.getHole2score());
            values.put(DBHelper.COLUMN_HOLE_3_SCORE, player.getHole3score());
            values.put(DBHelper.COLUMN_HOLE_4_SCORE, player.getHole4score());
            values.put(DBHelper.COLUMN_HOLE_5_SCORE, player.getHole5score());
            values.put(DBHelper.COLUMN_HOLE_6_SCORE, player.getHole6score());
            values.put(DBHelper.COLUMN_HOLE_7_SCORE, player.getHole7score());
            values.put(DBHelper.COLUMN_HOLE_8_SCORE, player.getHole8score());
            values.put(DBHelper.COLUMN_HOLE_9_SCORE, player.getHole9score());
            values.put(DBHelper.COLUMN_HOLE_10_SCORE, player.getHole10score());
            values.put(DBHelper.COLUMN_HOLE_11_SCORE, player.getHole11score());
            values.put(DBHelper.COLUMN_HOLE_12_SCORE, player.getHole12score());
            values.put(DBHelper.COLUMN_HOLE_13_SCORE, player.getHole13score());
            values.put(DBHelper.COLUMN_HOLE_14_SCORE, player.getHole14score());
            values.put(DBHelper.COLUMN_HOLE_15_SCORE, player.getHole15score());
            values.put(DBHelper.COLUMN_HOLE_16_SCORE, player.getHole16score());
            values.put(DBHelper.COLUMN_HOLE_17_SCORE, player.getHole17score());
            values.put(DBHelper.COLUMN_HOLE_18_SCORE, player.getHole18score());
        }

        mDatabase.insert(DBHelper.TABLE_PREVIOUS_GAMES, null, values);
    }

    public List<Game> getAllGames() {
        List<Game> gamesList = new ArrayList<Game>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_PREVIOUS_GAMES,
                mAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Game game = cursorToGame(cursor);
            gamesList.add(game);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return gamesList;
    }


    private Game cursorToGame(Cursor cursor) {
        Game game = new Game();
        // take care of basic field setting
        long gameID = cursor.getLong(0);
        game.setGameID(gameID);
        game.setDate(cursor.getLong(1));
        game.setLocation(cursor.getString(2));


        // have to do something a little different for players list - get game id, then query for all players with that game id
        ArrayList<Player> listPlayers = new ArrayList<>();

        Cursor playerCursor = mDatabase.query(DBHelper.TABLE_PREVIOUS_GAMES,
                mPlayerColumns, DBHelper.COLUMN_GAME_ID + "=?", new String[]{String.valueOf(gameID)}, null, null, null);

        playerCursor.moveToFirst();
        while (!playerCursor.isAfterLast()) {
            Player player = cursorToPlayer(playerCursor);
            listPlayers.add(player);
            playerCursor.moveToNext();
        }
        game.setPlayers(listPlayers);


        // make sure to close the cursors
        playerCursor.close();
        cursor.close();

        return game;
    }

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
