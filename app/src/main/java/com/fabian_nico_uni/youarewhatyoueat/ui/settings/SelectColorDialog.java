package com.fabian_nico_uni.youarewhatyoueat.ui.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.fabian_nico_uni.youarewhatyoueat.R;
import com.fabian_nico_uni.youarewhatyoueat.ui.home.AddCalsDialog;

import java.util.ArrayList;
import java.util.List;

public class SelectColorDialog extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener {
    public static final String LOG_TAG = SelectColorDialog.class.getSimpleName();

    private SelectColorDialogListener listener;

    private Spinner spinner;

    private Color selected;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater infalter = getActivity().getLayoutInflater();
        View view = infalter.inflate(R.layout.dialog_preference_color_select, null);

        spinner = view.findViewById(R.id.preference_color_spinner);
        List<Color> colorList = new ArrayList<>();
        Color orange = new Color("Orange", "#f78707");
        colorList.add(orange);
        Color yellow = new Color("Gelb", "#ebd407");
        colorList.add(yellow);
        Color blue = new Color("Blau", "#0876c9");
        colorList.add(blue);
        Color cyan = new Color("Cyan", "#04dede");
        colorList.add(cyan);
        Color green = new Color("Grün", "#00c421");
        colorList.add(green);

        selected = orange;

        ArrayAdapter<Color> adapter = new ArrayAdapter<Color>(getContext(), android.R.layout.simple_spinner_item, colorList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(adapter);

        builder.setView(view)
                .setTitle(R.string.preference_select_color_text)
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.applyColor(selected.color);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (SelectColorDialogListener) context;
        } catch (ClassCastException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected = (Color)parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface SelectColorDialogListener{
        void applyColor(String color);
    }

    public class Color {
        String label;
        String color;
        public Color(String label, String color) {
            this.label = label;
            this.color = color;
        }
        public String getLabel(){return label;}
        public String getColor(){return color;}

        @Override
        public String toString() {
            return getLabel();
        }
    }
}
