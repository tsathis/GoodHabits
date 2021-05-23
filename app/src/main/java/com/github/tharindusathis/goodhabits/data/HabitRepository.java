package com.github.tharindusathis.goodhabits.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.github.tharindusathis.goodhabits.model.Habit;

import java.util.List;

public class HabitRepository {

    private final HabitDao habitDao;
    private final LiveData<List<Habit>> allHabits;

    public HabitRepository(Application application) {
        HabitDatabase habitDatabase = HabitDatabase.getDatabase(application);
        this.habitDao = habitDatabase.habitDao();
        this.allHabits = habitDao.getAll();
    }

    public LiveData<List<Habit>> getAllHabits() {
        return allHabits;
    }

    public LiveData<Habit> getHabit(long habitId) {
        return habitDao.get(habitId);
    }

    public void insertHabit(Habit habit) {
        HabitDatabase.databaseWriteExecutor.execute(() -> habitDao.insert(habit));
    }

    public void updateHabit(Habit habit) {
        HabitDatabase.databaseWriteExecutor.execute(() -> habitDao.update(habit));
    }

    public void deleteHabit(Habit habit) {
        HabitDatabase.databaseWriteExecutor.execute(() -> habitDao.delete(habit));
    }

}
