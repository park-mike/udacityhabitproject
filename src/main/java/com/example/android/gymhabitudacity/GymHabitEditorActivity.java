package com.example.android.gymhabitudacity;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.gymhabitudacity.GymContract.GymEntry;


public class GymHabitEditorActivity extends AppCompatActivity {


    private EditText mNameEditText;

    private EditText mMuscleEditText;

    private EditText mRepsEditText;

    private Spinner mDifficultySpinner;

    private int mDiff = GymEntry.EASY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);


        mNameEditText = (EditText) findViewById(R.id.edit_exercise_name);
        mMuscleEditText = (EditText) findViewById(R.id.edit_muscle);
        mRepsEditText = (EditText) findViewById(R.id.edit_reps);
        mDifficultySpinner = (Spinner) findViewById(R.id.spinner_diff);

        setupSpinner();
    }

    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter diffSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_diff_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        diffSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mDifficultySpinner.setAdapter(diffSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mDifficultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.easy))) {
                        mDiff = GymEntry.EASY;
                    } else if (selection.equals(getString(R.string.medium))) {
                        mDiff = GymEntry.MEDIUM;
                    } else {
                        mDiff = GymEntry.HARD;
                    }
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mDiff = GymEntry.EASY;
            }
        });
    }

    private void insertExercise() {
        String nameString = mNameEditText.getText().toString().trim();
        String muscleString = mMuscleEditText.getText().toString().trim();
        String repsString = mRepsEditText.getText().toString().trim();
        int reps = Integer.parseInt(repsString);

        GymDbHelper mDbHelper = new GymDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GymEntry.COLUMN_EX_NAME, nameString);
        values.put(GymEntry.COLUMN_EX_MUSCLE, muscleString);
        values.put(GymEntry.COLUMN_EX_DIFF, mDiff);
        values.put(GymEntry.COLUMN_EX_REPS, reps);


        long newRowId = db.insert(GymEntry.TABLE_NAME, null, values);


        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error saving exercise", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Exercise saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save pet to database
                insertExercise();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}