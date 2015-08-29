package kohlerbear.com.minigolfer.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TAG = "DBHelper";

    // columns of the current_game table
    public static final String TABLE_CURRENT_GAME   = "current_game";
    public static final String COLUMN_PLAYER_ID     = "player_id";
    public static final String COLUMN_PLAYER_NAME   = "player_name";
    public static final String COLUMN_GOLFBALL_COLOR = "golf_ball_color";
    public static final String COLUMN_HOLE_1_SCORE  = "hole1score";
    public static final String COLUMN_HOLE_2_SCORE  = "hole2score";
    public static final String COLUMN_HOLE_3_SCORE  = "hole3score";
    public static final String COLUMN_HOLE_4_SCORE  = "hole4score";
    public static final String COLUMN_HOLE_5_SCORE  = "hole5score";
    public static final String COLUMN_HOLE_6_SCORE  = "hole6score";
    public static final String COLUMN_HOLE_7_SCORE  = "hole7score";
    public static final String COLUMN_HOLE_8_SCORE  = "hole8score";
    public static final String COLUMN_HOLE_9_SCORE  = "hole9score";
    public static final String COLUMN_HOLE_10_SCORE = "hole10score";
    public static final String COLUMN_HOLE_11_SCORE = "hole11score";
    public static final String COLUMN_HOLE_12_SCORE = "hole12score";
    public static final String COLUMN_HOLE_13_SCORE = "hole13score";
    public static final String COLUMN_HOLE_14_SCORE = "hole14score";
    public static final String COLUMN_HOLE_15_SCORE = "hole15score";
    public static final String COLUMN_HOLE_16_SCORE = "hole16score";
    public static final String COLUMN_HOLE_17_SCORE = "hole17score";
    public static final String COLUMN_HOLE_18_SCORE = "hole18score";



    // columns of the previous_game table
    public static final String TABLE_PREVIOUS_GAMES = "previous_games";
    public static final String COLUMN_GAME_ID = "game_id";
    public static final String COLUMN_GAME_DATE = "game_date";
    public static final String COLUMN_GAME_LOC = "game_location";
//    public static final String COLUMN_PLAYER_ID = COLUMN_PLAYER_ID;
//    public static final String COLUMN_PLAYER_NAME   = "player_name";
//    ...holes 1-18 - winner can be ascertained from these scores



    private static final String DATABASE_NAME = "minigolfer.db";
    private static final int DATABASE_VERSION = 2;
    //version 1 - base
    //version 2 -  adding date column to PREVIOUS_GAMES table

    // SQL statement of the previous_game table creation
    private static final String SQL_CREATE_TABLE_PREVIOUS_GAME = "CREATE TABLE " + TABLE_PREVIOUS_GAMES + "("
            + COLUMN_GAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_GAME_DATE + " INTEGER, " // unix time (integer scales so this is essentially our "long"
            + COLUMN_GAME_LOC + " TEXT, "
            + COLUMN_PLAYER_ID + " INTEGER, "
            + COLUMN_PLAYER_NAME + " TEXT NOT NULL, "
            + COLUMN_GOLFBALL_COLOR + " TEXT, " //whether or not this will actually be implemented is still up in the air
            + COLUMN_HOLE_1_SCORE + " INTEGER, "
            + COLUMN_HOLE_2_SCORE + " INTEGER, "
            + COLUMN_HOLE_3_SCORE + " INTEGER, "
            + COLUMN_HOLE_4_SCORE + " INTEGER, "
            + COLUMN_HOLE_5_SCORE + " INTEGER, "
            + COLUMN_HOLE_6_SCORE + " INTEGER, "
            + COLUMN_HOLE_7_SCORE + " INTEGER, "
            + COLUMN_HOLE_8_SCORE + " INTEGER, "
            + COLUMN_HOLE_9_SCORE + " INTEGER, "
            + COLUMN_HOLE_10_SCORE + " INTEGER, "
            + COLUMN_HOLE_11_SCORE + " INTEGER, "
            + COLUMN_HOLE_12_SCORE + " INTEGER, "
            + COLUMN_HOLE_13_SCORE + " INTEGER, "
            + COLUMN_HOLE_14_SCORE + " INTEGER, "
            + COLUMN_HOLE_15_SCORE + " INTEGER, "
            + COLUMN_HOLE_16_SCORE + " INTEGER, "
            + COLUMN_HOLE_17_SCORE + " INTEGER, "
            + COLUMN_HOLE_18_SCORE + " INTEGER "
            +");";

    // SQL statement of the current_game table creation
    private static final String SQL_CREATE_TABLE_CURRENT_GAME = "CREATE TABLE " + TABLE_CURRENT_GAME + "("
            + COLUMN_PLAYER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PLAYER_NAME + " TEXT NOT NULL, "
            + COLUMN_GOLFBALL_COLOR + " TEXT, "
            + COLUMN_HOLE_1_SCORE + " INTEGER, "
            + COLUMN_HOLE_2_SCORE + " INTEGER, "
            + COLUMN_HOLE_3_SCORE + " INTEGER, "
            + COLUMN_HOLE_4_SCORE + " INTEGER, "
            + COLUMN_HOLE_5_SCORE + " INTEGER, "
            + COLUMN_HOLE_6_SCORE + " INTEGER, "
            + COLUMN_HOLE_7_SCORE + " INTEGER, "
            + COLUMN_HOLE_8_SCORE + " INTEGER, "
            + COLUMN_HOLE_9_SCORE + " INTEGER, "
            + COLUMN_HOLE_10_SCORE + " INTEGER, "
            + COLUMN_HOLE_11_SCORE + " INTEGER, "
            + COLUMN_HOLE_12_SCORE + " INTEGER, "
            + COLUMN_HOLE_13_SCORE + " INTEGER, "
            + COLUMN_HOLE_14_SCORE + " INTEGER, "
            + COLUMN_HOLE_15_SCORE + " INTEGER, "
            + COLUMN_HOLE_16_SCORE + " INTEGER, "
            + COLUMN_HOLE_17_SCORE + " INTEGER, "
            + COLUMN_HOLE_18_SCORE + " INTEGER "
            +");";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_TABLE_CURRENT_GAME);
        database.execSQL(SQL_CREATE_TABLE_PREVIOUS_GAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG,
                "Upgrading the database from version " + oldVersion + " to " + newVersion);
        // clear all data
        //TODO note that this upgrade will clear user data!! Refactor this to NOT drop tables and rather modify them if possible (or needed at all)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREVIOUS_GAMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENT_GAME);

        // recreate the tables
        onCreate(db);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
}
