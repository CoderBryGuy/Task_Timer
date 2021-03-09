package com.example.task_timer;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    String[] projection = {TasksContract.Columns.TASKS_NAME, TasksContract.Columns.TASKS_DESCRIPTION};
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(TasksContract.buildTaskUri(2),
                projection,
                null,
                null,
                TasksContract.Columns.TASKS_SORTORDER);

        if(cursor != null){
            Log.d(TAG, "onCreate: number of rows: " + cursor.getCount());
            while(cursor.moveToNext()){
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    Log.d(TAG, "onCreate: " + cursor.getColumnName(i) + ": " + cursor.getString(i));
                }
                Log.d(TAG, "onCreate: =====================================================");
            }
            cursor.close();
        }

//        AppDatabase appDatabase = AppDatabase.getInstance(this);
//        final SQLiteDatabase db = appDatabase.getReadableDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}