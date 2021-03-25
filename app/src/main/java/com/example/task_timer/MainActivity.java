package com.example.task_timer;

import android.content.ContentResolver;
import android.content.ContentValues;
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

    String[] projection = {TasksContract.Columns._ID,
                            TasksContract.Columns.TASKS_NAME,
                            TasksContract.Columns.TASKS_DESCRIPTION,
                            TasksContract.Columns.TASKS_SORTORDER};

        ContentResolver contentResolver = getContentResolver();

        ContentValues values = new ContentValues();


//        values.put(TasksContract.Columns.TASKS_DESCRIPTION, "For deletion");
//        String selection = TasksContract.Columns.TASKS_SORTORDER + " = ?";
//
//        String[] args = {"99"};
//
//        int count = contentResolver.update(TasksContract.CONTENT_URI, values, selection, null);
//        Log.d(TAG, "onCreate: " + count + " record(s) updated");

//        values.put(TasksContract.Columns.TASKS_SORTORDER, "99");
//        values.put(TasksContract.Columns.TASKS_DESCRIPTION, "Completed");
//        String selection = TasksContract.Columns.TASKS_SORTORDER + " = " + 2;
//        int count = contentResolver.update(TasksContract.CONTENT_URI, values, selection, null);
//        Log.d(TAG, "onCreate: " + count + " record(s) updated");

//        values.put(TasksContract.Columns.TASKS_NAME, "Content Provider");
//        values.put(TasksContract.Columns.TASKS_DESCRIPTION, "Record content provider video");
//        values.put(TasksContract.Columns.TASKS_SORTORDER, 2);
//        int count = contentResolver.update(TasksContract.buildTaskUri(2), values, null, null);
//        Log.d(TAG, "onCreate: " + count + " record(s) updated");
//
//        values.put(TasksContract.Columns.TASKS_SORTORDER, 3);
//        count = contentResolver.update(TasksContract.buildTaskUri(3), values, null, null);
//        Log.d(TAG, "onCreate: " + count + " record(s) updated");

//        values.put(TasksContract.Columns.TASKS_NAME, "New Task 2");
//        values.put(TasksContract.Columns.TASKS_DESCRIPTION, "Description 2");
//        values.put(TasksContract.Columns.TASKS_SORTORDER, 3);
//        Uri uri = contentResolver.insert(TasksContract.CONTENT_URI, values);
//
//        values.put(TasksContract.Columns.TASKS_NAME, "New Task 4");
//        values.put(TasksContract.Columns.TASKS_DESCRIPTION, "Description 4");
//        values.put(TasksContract.Columns.TASKS_SORTORDER, 4);
//        Uri uri = contentResolver.insert(TasksContract.CONTENT_URI, values);

//        int count = contentResolver.delete(TasksContract.buildTaskUri(3), null, null);

        String selection = TasksContract.Columns.TASKS_DESCRIPTION + " = ?";
        String[] args = { "For deletion"};
        int count = contentResolver.delete(TasksContract.CONTENT_URI, selection, args);
        Log.d(TAG, "onCreate: " + count + " records(s) deleted");

//        Cursor cursor = contentResolver.query(TasksContract.buildTaskUri(2),
        Cursor cursor = contentResolver.query(TasksContract.CONTENT_URI,
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