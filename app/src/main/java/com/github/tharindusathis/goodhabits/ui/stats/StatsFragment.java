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
import com.github.tharindusathis.goodhabits.util.ProgressTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class StatsFragment extends Fragment {

    private StatsViewModel statsViewModel;
    private HabitAndroidViewModel habitAndroidViewModel;
    private TextView textTitleCard1;
    private TextView textHeadingCard1;
    private TextView textBodyCard1;
    private TextView textTitleCard2;
    private TextView textHeadingCard2;
    private TextView textBodyCard2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        statsViewModel =
                new ViewModelProvider(this).get(StatsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stats, container, false);

        textTitleCard1 = root.findViewById(R.id.textTitleCard1);
        textHeadingCard1 = root.findViewById(R.id.textHeadingCard1);
        textBodyCard1 = root.findViewById(R.id.textBodyCard1);
        textTitleCard2 = root.findViewById(R.id.textTitleCard2);
        textHeadingCard2 = root.findViewById(R.id.textHeadingCard2);
        textBodyCard2 = root.findViewById(R.id.textBodyCard2);

        habitAndroidViewModel = new ViewModelProvider.AndroidViewModelFactory(
                requireActivity().getApplication()
        ).create(HabitAndroidViewModel.class);

        initText();

        return root;
    }

    private void initText() {
        textTitleCard1.setText(R.string.stats_total);
        habitAndroidViewModel.countHabits().observe(requireActivity(), countLong ->
                {
                    textHeadingCard1.setText(String.format(Locale.ENGLISH, "%d total habits are growing..! ", countLong));
                    textBodyCard1.setText(R.string.stats_good_feedback);
                }
        );


        textTitleCard2.setText(R.string.stats_best_performance_title);
        habitAndroidViewModel.getFirstStartedHabit().observe(requireActivity(), habit ->
                {
                    textHeadingCard2.setText(habit.getTitle());
                    String startAt = "Growing since " +
                            new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)
                                    .format(habit.startedAt);
                    textBodyCard2.setText(startAt);
                }
        );
    }

}