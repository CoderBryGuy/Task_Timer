                                                                                                                                                                                                                         package com.example.task_timer;

                                                                                                                                                                                                                         import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements CursorRecyclerViewAdapter.OnTaskClickListener,
        AddEditActivityFragment.OnSaveListener {

    private static final String TAG = "MainActivity";

    //whether the activity is in 2-pane mode
    //i.e. runing in landscape mode on a tablet
    private boolean mTwoPane = false;

    private static final String ADD_EDIT_FRAGMENT = "AddEditFragment";
    private AddEditActivityFragment mAddEditActivityFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.main_fragment_container_view, MainActivityFragment.class, null)
                    .commit();
        }

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            mTwoPane = true;
        } else {
            // In portrait
            mTwoPane = false;
        }


//        if(findViewById(R.id.task_details_container) != null){
//            //the detail container view will be present only in the large-screen layouts (res/values-land and res-values-sw600dp)
//            //if this view is present, then the activity should be in two-pane mode
//            mTwoPane = true;
//        }

        Log.d(TAG, "onCreate: mTwoPane = " + mTwoPane);

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
        switch (id) {
            case R.id.menumain_addTask:
                taskEditRequest(null);
                break;
            case R.id.menumain_showDurations:
                break;
            case R.id.menuMain_settings:
                break;
            case R.id.menuMain_showAbout:
                break;
            case R.id.menuMain_generate:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void taskEditRequest(Task task){
        Log.d(TAG, "taskEditRequest: starts");
        if(mTwoPane){
            Log.d(TAG, "taskEditRequest: in two pane mode (tablet)");
            mAddEditActivityFragment = new AddEditActivityFragment();
            mAddEditActivityFragment.onAttach((Context)this);

            Bundle arguments = new Bundle();
            arguments.putSerializable(Task.class.getSimpleName(), task);
            mAddEditActivityFragment.setArguments(arguments);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.task_details_container, mAddEditActivityFragment);
            fragmentTransaction.commit();
        }else{
            Log.d(TAG, "taskEditRequest: in single-pane mode (phone)");
            Intent detailedIntent = new Intent(this, AddEditActivity.class);
            if(task != null){
                //in single-pane mode, start the detail activity for the selected id
                detailedIntent.putExtra(Task.class.getSimpleName(), task);
                startActivity(detailedIntent);
            }else{
                //add a new task
                startActivity(detailedIntent);
            }
        }
    }

    @Override
    public void onEditClick(Task task) {
        taskEditRequest(task);
    }

    @Override
    public void onDeleteClick(Task task) {
        getContentResolver().delete(TasksContract.buildTaskUri(task.getId()), null, null);
    }

    @Override
    public void onSaveClick() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(mAddEditActivityFragment);
        fragmentTransaction.commit();
    }
}