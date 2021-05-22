package com.github.tharindusathis.goodhabits.ui.habit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.tharindusathis.goodhabits.R;
import com.github.tharindusathis.goodhabits.model.Habit;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class HabitRecyclerViewAdapter extends RecyclerView.Adapter<HabitRecyclerViewAdapter.ViewHolder> {

    private final List<Habit> habitList;

    public HabitRecyclerViewAdapter(List<Habit> habitList) {
        this.habitList = habitList;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.habit_recycler_view_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        Habit habit = habitList.get(position);
        holder.textViewHabitTitle.setText(habit.getTitle());
    }

    @Override
    public int getItemCount() {
        return habitList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewHabitTitle;

        public ViewHolder(@NotNull View itemView) {
            super(itemView);
            textViewHabitTitle = (TextView) itemView.findViewById(R.id.text_habit_title);
        }
    }
}
