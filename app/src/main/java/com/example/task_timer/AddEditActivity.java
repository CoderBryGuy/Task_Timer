package com.example.task_timer;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AddEditActivity extends AppCompatActivity implements AddEditActivityFragment.OnSaveListener {
    private static final String TAG = "AddEditActivity";
    private Task task = null;
    AddEditActivityFragment mAddEditFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAddEditFragment = new AddEditActivityFragment();
        mAddEditFragment.onAttach((Context)this);

        Bundle arguments = getIntent().getExtras();
        mAddEditFragment.setArguments(arguments);

        if (savedInstanceState == null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.addedit_activity_container_view, mAddEditFragment);
            fragmentTransaction.commit();

        }
    }

    @Override
    public void onSaveClick() {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.remove(mFragment);
//        fragmentTransaction.commit();

        finish();
    }
}