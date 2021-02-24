package com.example.task_timer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * basic database class for the appplication
 *
 * the only class that should use this AppProvider
 */

public class AppDatabase extends SQLiteOpenHelper {
    private static final String TAG = "AppDatabase";
    public static final String DATABASE_NAME = "TaskTimer.db";
    public static final int DATABASE_VERSION = 1;
    // implement AppDatabase as a Singleton
    private static AppDatabase instance = null;

    private AppDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "AppDatabase: construct");
    }

    /**
     * Get instance of app singleton database helper object
     * @param context the content providers context
     * @return a SQLite database helper object
     */
    static AppDatabase getInstance(Context context){
                if(instance == null){
                    Log.d(TAG, "getInstance: creating new instance");
                    instance = new AppDatabase(context);
                }
                return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
