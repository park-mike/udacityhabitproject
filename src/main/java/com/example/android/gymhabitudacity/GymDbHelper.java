package com.example.android.gymhabitudacity;

/**
 * Created by MP on 4/9/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.gymhabitudacity.GymContract.GymEntry;

/**
 * Database helper for Pets app. Manages database creation and version management.
 */
public class GymDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = GymDbHelper.class.getSimpleName();

    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "gym.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link GymDbHelper}.
     *
     * @param context of the app
     */
    public GymDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_GYM_TABLE = "CREATE TABLE " + GymEntry.TABLE_NAME + " ("
                + GymEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + GymEntry.COLUMN_EX_NAME + " TEXT NOT NULL, "
                + GymEntry.COLUMN_EX_MUSCLE + " TEXT, "
                + GymEntry.COLUMN_EX_DIFF + " INTEGER NOT NULL, "
                + GymEntry.COLUMN_EX_REPS + " INTEGER NOT NULL DEFAULT 0);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_GYM_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}