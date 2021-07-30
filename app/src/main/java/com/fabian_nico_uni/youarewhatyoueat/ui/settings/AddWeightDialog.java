package com.fabian_nico_uni.youarewhatyoueat.ui.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.fabian_nico_uni.youarewhatyoueat.R;
import com.fabian_nico_uni.youarewhatyoueat.ui.home.AddCalsDialog;

public class AddWeightDialog extends AppCompatDialogFragment {
    public static final String LOG_TAG = AddWeightDialog.class.getSimpleName();

    private EditText weightInput;

    private AddWeightDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater infalter = getActivity().getLayoutInflater();
        View view = infalter.inflate(R.layout.dialog_preference_weight_add, null);

        weightInput = view.findViewById(R.id.preference_add_weight_value);

        builder.setView(view)
                .setTitle(R.string.preference_add_weight)
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int weight = Integer.parseInt(weightInput.getText().toString());
                        listener.applyWeight(weight);
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (AddWeightDialogListener) context;
        } catch (ClassCastException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
    }

    public interface AddWeightDialogListener{
        void applyWeight(int weight);
    }
}
