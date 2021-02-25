package com.example.task_timer;

import android.provider.BaseColumns;

public class TasksContract {

    public static final String TABLE_NAME = "Tasks";

    //Task fields
    public static class Columns{
        public static final String _ID = BaseColumns._ID;
        public static final String TASKS_NAME = "Name";
        public static final String TASKS_DESCRIPTION = "Description";
        public static final String TASKS_SORTORDER = "SortOrder";

        private Columns(){
            //private constructor to prevent instantiation
        }


    }

}
