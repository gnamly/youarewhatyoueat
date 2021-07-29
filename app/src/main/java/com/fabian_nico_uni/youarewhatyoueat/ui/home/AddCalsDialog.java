package com.fabian_nico_uni.youarewhatyoueat.ui.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.fabian_nico_uni.youarewhatyoueat.R;
import com.fabian_nico_uni.youarewhatyoueat.data.Profile;

public class AddCalsDialog extends AppCompatDialogFragment {
    public static final String LOG_TAG = AddCalsDialog.class.getSimpleName();
    private EditText addInput;

    private AddCalsDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater infalter = getActivity().getLayoutInflater();
        View view = infalter.inflate(R.layout.dialog_fragment_home_add, null);

        addInput = view.findViewById(R.id.home_add_cal_value);

        builder.setView(view)
                .setTitle(R.string.home_add)
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int cal = Integer.parseInt(addInput.getText().toString());
                        listener.applyCals(cal);
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(LOG_TAG, "context is "+context);

        try {
            listener = (AddCalsDialogListener)context;
        } catch (ClassCastException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
    }

    public interface AddCalsDialogListener{
        void applyCals(int cal);
    }
}
