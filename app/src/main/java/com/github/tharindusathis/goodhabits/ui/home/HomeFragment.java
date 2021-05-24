package com.github.tharindusathis.goodhabits.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.tharindusathis.goodhabits.R;
import com.github.tharindusathis.goodhabits.model.HabitAndroidViewModel;
import com.github.tharindusathis.goodhabits.ui.habit.HabitRecyclerViewAdapter;

public class HomeFragment extends Fragment {

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
                            habitRecyclerViewAdapter = new HabitRecyclerViewAdapter(habits);
                            habitRecyclerView.setAdapter(habitRecyclerViewAdapter);
                        }
                );

        return root;
    }
}