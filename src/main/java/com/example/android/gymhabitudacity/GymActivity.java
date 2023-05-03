package com.example.android.gymhabitudacity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.gymhabitudacity.GymContract.GymEntry;

/**
 * Created by MP on 4/9/2017.
 */

public class GymActivity extends AppCompatActivity {

    /**
     * Database helper that will provide us access to the database
     */
    private GymDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GymActivity.this, GymEditor.class);
                startActivity(intent);
            }
        });

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new GymDbHelper(this);
        // Cursor habitAppCursor = readCursor();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private Cursor displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                GymContract.GymEntry._ID,
                GymEntry.COLUMN_EX_NAME,
                GymEntry.COLUMN_EX_MUSCLE,
                GymEntry.COLUMN_EX_DIFF,
                GymEntry.COLUMN_EX_REPS};

        // Perform a query on the pets table
        Cursor cursor = db.query(
                GymContract.GymEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        cursor.close();
        return cursor;
    }

    public Cursor readCursor(int id) {
        Cursor cursor;
        TextView displayView = (TextView) findViewById(R.id.text_view_pet);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String query = "SELECT * FROM GymContract.GymEntry.TABLE_NAME WHERE GymContract.GymEntry._ID = null";
        cursor = db.rawQuery(query, null);
        displayView.setText(query);
        cursor.close();
        return cursor;
    }


    private void insertExercise() {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(GymEntry.COLUMN_EX_NAME, "Lat pulldown");
        values.put(GymEntry.COLUMN_EX_MUSCLE, "Latisimus");
        values.put(GymEntry.COLUMN_EX_DIFF, GymEntry.EASY);
        values.put(GymEntry.COLUMN_EX_REPS, 12);

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the pets table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto.
        long newRowId = db.insert(GymContract.GymEntry.TABLE_NAME, null, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertExercise();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                deleteDatabase();
                displayDatabaseInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteDatabase() {
        Context context = GymActivity.this;
        context.deleteDatabase("gym.db");
        mDbHelper = new GymDbHelper(this);

    }
}

