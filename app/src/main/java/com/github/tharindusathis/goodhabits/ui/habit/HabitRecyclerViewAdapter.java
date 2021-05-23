package com.github.tharindusathis.goodhabits.ui.habit;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.tharindusathis.goodhabits.R;
import com.github.tharindusathis.goodhabits.model.Habit;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;
import java.util.Locale;


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

        holder.startProgressTimer(habit.startedAt);
    }

    @Override
    public int getItemCount() {
        return habitList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewHabitTitle;
        public TextView textHabitProgressTimer;

        public CountDownTimer habitProgressTimer;

        public ViewHolder(@NotNull View itemView) {
            super(itemView);
            textViewHabitTitle = itemView.findViewById(R.id.text_habit_title);
            textHabitProgressTimer = itemView.findViewById(R.id.text_habit_progress_timer);
        }

        public void setHabitProgressTime(long timeInMillis) {
            if (textHabitProgressTimer != null) {
                long seconds = timeInMillis / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                long days = hours / 24;
                String time = String.format(
                        Locale.ENGLISH,
                        "%dd %dh %dm %ds",
                        days, hours % 24, minutes % 60, seconds % 60
                );
                textHabitProgressTimer.setText(time);
            }
        }


        public void startProgressTimer(Date startedAt) {
            if (habitProgressTimer != null) {
                habitProgressTimer.cancel();
            }
            long currentTimeUpdateIntervalMillis = 3600_000;
            long countDownInterval = 1000;
            habitProgressTimer = new HabitProgressTimer(
                    currentTimeUpdateIntervalMillis,
                    countDownInterval,
                    startedAt
            ).start();
        }

        public class HabitProgressTimer extends CountDownTimer {
            long currentTime;
            long countDownInterval;
            long startedAtTime;

            public HabitProgressTimer(long millisInFuture, long countDownInterval, Date startedAt) {
                super(millisInFuture, countDownInterval);
                this.countDownInterval = countDownInterval;

                if (startedAt == null) {
                    startedAtTime = new Date().getTime();
                } else {
                    startedAtTime = startedAt.getTime();
                }

                setCurrentTime();
            }

            private void setCurrentTime() {
                this.currentTime = (new Date().getTime() - startedAtTime);
            }

            @Override
            public void onTick(long millisUntilFinished) {
                currentTime += countDownInterval;
                setHabitProgressTime(currentTime);
            }

            @Override
            public void onFinish() {
                setCurrentTime();
                this.start();
            }
        }
    }
}
