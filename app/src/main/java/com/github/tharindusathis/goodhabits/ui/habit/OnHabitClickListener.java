package com.github.tharindusathis.goodhabits.ui.habit;

import com.github.tharindusathis.goodhabits.model.Habit;

public interface OnHabitClickListener {
    void onHabitClick(Habit habit);
    void onHabitLongClick(Habit habit);
}
