package com.github.tharindusathis.goodhabits.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.tharindusathis.goodhabits.R;
import com.github.tharindusathis.goodhabits.model.Habit;
import com.github.tharindusathis.goodhabits.model.HabitAndroidViewModel;
import com.github.tharindusathis.goodhabits.ui.habit.HabitRecyclerViewAdapter;
import com.github.tharindusathis.goodhabits.ui.habit.OnHabitClickListener;

public class HomeFragment extends Fragment implements OnHabitClickListener {

    private static final String TAG = "HOME_FRAGMENT";

    private RecyclerView habitRecyclerView;
    private HabitRecyclerViewAdapter habitRecyclerViewAdapter;
    private HabitAndroidViewModel habitAndroidViewModel;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        habitRecyclerView = (RecyclerView) root.findViewById(R.id.habit_recycler_view);
        habitRecyclerView.setHasFixedSize(true);
        habitRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        habitAndroidViewModel = new ViewModelProvider.AndroidViewModelFactory(
                requireActivity().getApplication()
        ).create(HabitAndroidViewModel.class);

        habitAndroidViewModel
                .getAllHabits()
                .observe(requireActivity(), habits -> {
                            habitRecyclerViewAdapter = new HabitRecyclerViewAdapter(habits, this);
                            habitRecyclerView.setAdapter(habitRecyclerViewAdapter);
                        }
                );

        return root;
    }

    @Override
    public void onHabitClick(Habit habit) {
        System.out.println("CLicked");
        Log.d(TAG,"Clicked: " + habit);
    }
}