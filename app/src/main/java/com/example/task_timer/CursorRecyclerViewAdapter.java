package com.example.task_timer;

import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class CursorRecyclerViewAdapter extends RecyclerView.Adapter<CursorRecyclerViewAdapter.TaskViewHolder> {
    private static final String TAG = "CursorRecyclerViewAdapt";
    private Cursor mCursor;

    public CursorRecyclerViewAdapter(Cursor cursor) {
        Log.d(TAG, "CursorRecyclerViewAdapter: Constructor called");
        mCursor = cursor;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: new view requested");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_items, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
            if((mCursor ==  null) ||(mCursor.getCount() == 0)){
                Log.d(TAG, "onBindViewHolder: providing instructions");
                holder.name.setText("Instructions");
                holder.description.setText("Use the add button (+) in the toolbar above to create new tasks." +
                        "\n\nTasks with lower sort orders will be placed higher up in the list." +
                        "Tasks with the same sort order will be sorted alphabetically."+
                        "\n\nTapping a task will start the timer for that task (and will stop the timer for any previous task that was being timed"+
                        "\n\nEeach task has Edit and Delete buttons if you want to change the details and remove the task.");
                holder.editButton.setVisibility(View.GONE); //TODO add onClick Listener
                holder.deleteButton.setVisibility(View.GONE); //TODO add onClick Listener
            }else{
                if(!mCursor.moveToPosition(i)){
                    throw new IllegalStateException("Couldn't move cursor to position " + i);
                }
                holder.name.setText(mCursor.getString(mCursor.getColumnIndex(TasksContract.Columns.TASKS_NAME)));
                holder.description.setText(mCursor.getString(mCursor.getColumnIndex(TasksContract.Columns.TASKS_DESCRIPTION)));
                holder.editButton.setVisibility(View.VISIBLE);
                holder.deleteButton.setVisibility(View.VISIBLE);
            }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "TaskViewHolder";

        TextView name = null;
        TextView description = null;
        ImageButton editButton = null;
        ImageButton deleteButton = null;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "TaskViewHolder: starts");
            this.name = (TextView) itemView.findViewById(R.id.tli_name);
            this.description = (TextView) itemView.findViewById(R.id.tli_description);
            this.editButton = (ImageButton) itemView.findViewById(R.id.tli_edit);
            this.deleteButton = (ImageButton) itemView.findViewById(R.id.tli_edit);
        }
    }

}
