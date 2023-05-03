package com.example.android.gymhabitudacity;

/**
 * Created by MP on 4/9/2017.
 */

import android.provider.BaseColumns;

public final class GymContract {

    private GymContract() {
    }

    public static final class GymEntry implements BaseColumns {

        public final static String TABLE_NAME = "gym";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_EX_NAME = "name";

        public final static String COLUMN_EX_MUSCLE = "muscle";

        public final static String COLUMN_EX_DIFF = "difficulty";

        public final static String COLUMN_EX_REPS = "reps";

        public static final int EASY = 0;
        public static final int MEDIUM = 1;
        public static final int HARD = 2;
    }

}

