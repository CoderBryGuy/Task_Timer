package com.example.task_timer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEditActivityFragment#} factory method to
 * create an instance of this fragment.
 */
public class AddEditActivityFragment extends Fragment {
    private static final String TAG = "AddEditActivityFragment";
    public enum FragmentEditMode{EDIT, ADD}
    private FragmentEditMode mEditMode;

    private EditText nNameTextView;
    private EditText mDescriptionTextView;
    private EditText mSortOrderTextView;
    private Button mSaveButton;

    private EditText mNameTextView;
    private OnSaveListener mOnSaveListener;

    interface OnSaveListener{
       void onSaveClick();
    }


//    public AddEditActivityFragment(OnSaveListener onSaveListener) {
//        this.mOnSaveListener = onSaveListener;
//
//        Log.d(TAG, "AddEditActivityFragment: constructor called");
//    }

    public AddEditActivityFragment() {
        // Required empty public constructor
        Log.d(TAG, "AddEditActivityFragment: constructor called");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Log.d(TAG, "onAttach: starts");
        super.onAttach(context);

        Activity activity = (Activity) context;
        if(!(activity instanceof OnSaveListener)){
            throw new ClassCastException(
                    new StringBuilder()
                    .append(activity.getClass().getSimpleName())
                    .append(" must implement AddEditActivityFragment.OnSavedClicked interface")
                    .toString());
        }

        mOnSaveListener = (OnSaveListener)activity;
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach: ");
        super.onDetach();
        mOnSaveListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: start");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_edit, container, false);


        mNameTextView = (EditText) view.findViewById(R.id.addedit_name);
        mDescriptionTextView = (EditText) view.findViewById(R.id.addedit_description);
        mSortOrderTextView = (EditText) view.findViewById(R.id.addedit_sortorder);
        mSaveButton = (Button) view.findViewById(R.id.addedit_save);

//        Bundle arguments = getActivity().getIntent().getExtras();
        Bundle arguments = getArguments();

        final Task task;
        if(arguments != null){
            Log.d(TAG, "onCreateView: retrieving task details");
            task = (Task)arguments.getSerializable(Task.class.getSimpleName());
            if(task != null){
                Log.d(TAG, "onCreateView: Task details found, editing...");
                mNameTextView.setText(task.getName());
                mDescriptionTextView.setText(task.getDescription());
                mSortOrderTextView.setText(Integer.toString(task.getSortOrder()));
                mEditMode = FragmentEditMode.EDIT;
            }else{
                // no task, so we must be adding a new task, and not editing an existing one
                mEditMode = FragmentEditMode.ADD;
            }
        } else {
            task = null;
            Log.d(TAG, "onCreateView: No arguments, adding a new record");
            mEditMode = FragmentEditMode.ADD;
        }

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // update the database if at least one field is changed
                //- There's no need to hit the database unless this has happened.
                int so; //saved repeated conversions to int.
                if(mSortOrderTextView.length() > 0){
                    so = Integer.parseInt(mSortOrderTextView.getText().toString());
                }else{
                    so = 0;
                }

                ContentResolver contentResolver = getActivity().getContentResolver();
                ContentValues values = new ContentValues();

                switch(mEditMode){

                    case EDIT:
                        if(!mNameTextView.getText().toString().equals(task.getName())){
                            values.put(TasksContract.Columns.TASKS_NAME, mNameTextView.getText().toString());
                            if(!mDescriptionTextView.getText().toString().equals(task.getDescription())){
                                values.put(TasksContract.Columns.TASKS_DESCRIPTION, mDescriptionTextView.getText().toString());
                            }
                        }
                        if(so != task.getSortOrder()){
                            values.put(TasksContract.Columns.TASKS_SORTORDER, so);
                        }
                        if(values.size() != 0){
                            Log.d(TAG, "onClick: updating task");
                            contentResolver.update(TasksContract.buildTaskUri(task.getId()), values, null, null);
                        }
                        break;

                    case ADD:
                        if(mNameTextView.length() > 0){
                            Log.d(TAG, "onClick: adding new task");
                            values.put(TasksContract.Columns.TASKS_NAME, mNameTextView.getText().toString());
                            values.put(TasksContract.Columns.TASKS_DESCRIPTION, mDescriptionTextView.getText().toString());
                            values.put(TasksContract.Columns.TASKS_SORTORDER, so);
                            contentResolver.insert(TasksContract.CONTENT_URI, values);
                        }
                        break;
                }

                Log.d(TAG, "onClick: done editing");
                if(mOnSaveListener != null) {
                    mOnSaveListener.onSaveClick();
                }
            }
        });
        Log.d(TAG, "onCreateView: Exiting...");

        return view;
    }
}