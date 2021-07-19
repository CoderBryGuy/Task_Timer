package com.example.task_timer;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class AppDialog extends DialogFragment {
    private static final String TAG = "AppDialog";
    public static final String DIALOG_ID = "id";
    public static final String DIALOG_MESSAGE = "message";
    public static final String DIALOG_POSITIVE_RID = "positive_rid";
    public static final String DIALOG_NEGATIVE_RID = "negative_rid";

    /**
     * The dialog's callback interface to notify of user selected results (deletion confirmed, etc.).
     */

    interface DialogEvents{
        void onPositiveDialogResult(int dialogId, Bundle args);
        void onNegativeDialogResult(int dialogId, Bundle args);
        void onDialogCancelled(int dialogId);
    }

    private DialogEvents mDialogEvents;

    @Override
    public void onAttach(@NonNull Context context) {
        Log.d(TAG, "onAttach: entering onAttach, activity is " + context.toString());
        super.onAttach(context);

        //Activities containing this fragment must implement its callbacks

        if(!(context instanceof DialogEvents)){
            throw new ClassCastException(context.toString() + " must implement AppDialog.DialogEvents interface");
        }

        mDialogEvents = (DialogEvents) context;

    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach: entering...");
        super.onDetach();

        //reset active callbacks interface, because we don't have an activity any longer.
        mDialogEvents = null;

    }
}
