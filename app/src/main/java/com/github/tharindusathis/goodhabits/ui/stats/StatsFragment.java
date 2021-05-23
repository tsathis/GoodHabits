package com.github.tharindusathis.goodhabits.ui.stats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.tharindusathis.goodhabits.R;
import com.github.tharindusathis.goodhabits.model.HabitAndroidViewModel;

import java.util.Locale;

public class StatsFragment extends Fragment {

    private StatsViewModel statsViewModel;
    private HabitAndroidViewModel habitAndroidViewModel;
    private TextView textHabitsCount;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        statsViewModel =
                new ViewModelProvider(this).get(StatsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stats, container, false);
        final TextView textView = root.findViewById(R.id.text_stats);
        statsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        textHabitsCount = root.findViewById(R.id.text_habits_count);

        habitAndroidViewModel = new ViewModelProvider.AndroidViewModelFactory(
                requireActivity().getApplication()
        ).create(HabitAndroidViewModel.class);

        habitAndroidViewModel.countHabits().observe(requireActivity(), countLong ->
                textHabitsCount.setText(String.format(Locale.ENGLISH, "Total habits: %d", countLong))
        );

        return root;
    }
}