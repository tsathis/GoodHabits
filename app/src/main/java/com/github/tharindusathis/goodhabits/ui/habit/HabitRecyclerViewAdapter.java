package com.github.tharindusathis.goodhabits.ui.habit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.tharindusathis.goodhabits.R;
import com.github.tharindusathis.goodhabits.model.Habit;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class HabitRecyclerViewAdapter extends RecyclerView.Adapter<HabitViewHolder> {

    private final List<Habit> habitList;
    private final OnHabitClickListener onHabitClickListener;

    public HabitRecyclerViewAdapter(List<Habit> habitList, OnHabitClickListener onHabitClickListener) {
        this.habitList = habitList;
        this.onHabitClickListener = onHabitClickListener;
    }

    @NotNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.habit_recycler_view_item, parent, false);
        return new HabitViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NotNull HabitViewHolder holder, int position) {
        Habit habit = habitList.get(position);
        holder.textViewHabitTitle.setText(habit.getTitle());

        holder.startProgressTimer(habit.startedAt);
    }

    @Override
    public int getItemCount() {
        return habitList.size();
    }

    public List<Habit> getHabitList() {
        return habitList;
    }

    public OnHabitClickListener getOnHabitClickListener() {
        return onHabitClickListener;
    }
}
