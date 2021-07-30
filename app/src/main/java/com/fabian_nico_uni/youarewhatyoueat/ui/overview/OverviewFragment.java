package com.fabian_nico_uni.youarewhatyoueat.ui.overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.fabian_nico_uni.youarewhatyoueat.R;
import com.fabian_nico_uni.youarewhatyoueat.data.*;
import com.fabian_nico_uni.youarewhatyoueat.ui.home.HomeFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class OverviewFragment extends Fragment implements CurrentProfileUpdateEvent {
    public static final String LOG_TAG = OverviewFragment.class.getSimpleName();

    private OverviewViewModel overviewViewModel;

    private LineChart weightChart;
    private LineChart calsChart;

    ArrayList<Entry> weightChartEntries = new ArrayList<>();
    ArrayList<Entry> calsChartEntries = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        overviewViewModel = ViewModelProviders.of(this).get(OverviewViewModel.class);
        View root = inflater.inflate(R.layout.fragment_overview, container, false);

        weightChart = root.findViewById(R.id.overview_chart_weight);
        calsChart = root.findViewById(R.id.overview_chart_cals);

        ProfileManager.getInstance(getContext()).addCurrentProfileUpdateListener(this);
        onProfileUpdated(ProfileManager.getInstance(getContext()).getCurrent(), false);

        return root;
    }

    @Override
    public void onProfileUpdated(Profile current, boolean newProfile) {
        //update the charts using com.github.mikephil.charting charts
        if(current == null) return;
        LocalDateTime today = LocalDateTime.now();

        weightChartEntries.clear();
        for(Weight weight : current.weights) {
            if(weight.timestamp.getYear() == today.getYear() && weight.timestamp.getMonth() == today.getMonth())
                weightChartEntries.add(new Entry(weight.timestamp.getDayOfMonth(), weight.weight));
        }
        LineDataSet weightDataSet = new LineDataSet(weightChartEntries, "Weight");
        weightDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        LineData weightData = new LineData(weightDataSet);


        weightChart.setData(weightData);
        weightChart.getDescription().setText("Körpergewicht diesen Monat");
        weightChart.animateY(0);

        calsChartEntries.clear();
        for(Calories cal : current.calories) {
                calsChartEntries.add(new Entry(cal.timestamp.getHour(), cal.calories));
        }
        LineDataSet calsDataSet = new LineDataSet(calsChartEntries, "Calories");
        calsDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        LineData calsData = new LineData(calsDataSet);

        calsChart.setData(calsData);
        calsChart.getDescription().setText("Kalorien Heute");
        calsChart.animateY(0);
    }
}
