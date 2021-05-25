package com.github.tharindusathis.goodhabits.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.github.tharindusathis.goodhabits.model.Habit;

import java.util.Date;
import java.util.List;

public class HabitRepository {

    private final HabitDao habitDao;
    private final LiveData<List<Habit>> allHabits;
    private final LiveData<Long> habitsCounts;

    public HabitRepository(Application application) {
        HabitDatabase habitDatabase = HabitDatabase.getDatabase(application);
        this.habitDao = habitDatabase.habitDao();
        this.allHabits = habitDao.getAll();
        this.habitsCounts = habitDao.count();
    }

    public LiveData<List<Habit>> getAllHabits() {
        return allHabits;
    }

    public LiveData<Habit> getHabit(long habitId) {
        return habitDao.get(habitId);
    }

    public LiveData<Long> countHabits() {
        return habitsCounts;
    }

    public LiveData<Habit> getFirstStartedHabit() {
        return habitDao.getFirstStarted();
    }

    public void insertHabit(Habit habit) {
        sanitizeHabit(habit);
        HabitDatabase.databaseWriteExecutor.execute(() -> habitDao.insert(habit));
    }

    public void updateHabit(Habit habit) {
        sanitizeHabit(habit);
        HabitDatabase.databaseWriteExecutor.execute(() -> habitDao.update(habit));
    }

    public void deleteHabit(Habit habit) {
        HabitDatabase.databaseWriteExecutor.execute(() -> habitDao.delete(habit));
    }

    public void deleteLastAddedHabit() {
        HabitDatabase.databaseWriteExecutor.execute(habitDao::deleteLastAdded);
    }

    private void sanitizeHabit(Habit habit) {
        if(habit.getStartedAt() == null) {
            habit.setStartedAt(new Date());
        }
    }

}
