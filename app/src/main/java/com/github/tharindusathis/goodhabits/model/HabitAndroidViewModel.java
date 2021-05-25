package com.github.tharindusathis.goodhabits.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.github.tharindusathis.goodhabits.data.HabitDatabase;
import com.github.tharindusathis.goodhabits.data.HabitRepository;

import java.util.List;

public class HabitAndroidViewModel extends AndroidViewModel {

    public static HabitRepository habitRepository;
    public final LiveData<List<Habit>> allHabits;

    public HabitAndroidViewModel(@NonNull Application application) {
        super(application);
        habitRepository = new HabitRepository(application);
        allHabits = habitRepository.getAllHabits();
    }

    public LiveData<List<Habit>> getAllHabits() {
        return allHabits;
    }

    public LiveData<Habit> getHabit(long habitId) {
        return habitRepository.getHabit(habitId);
    }

    public LiveData<Long> countHabits() {
        return habitRepository.countHabits();
    }

    public LiveData<Habit> getFirstStartedHabit() {
        return habitRepository.getFirstStartedHabit();
    }

    public static void insertHabit(Habit habit) {
        habitRepository.insertHabit(habit);
    }

    public static void updateHabit(Habit habit) {
        habitRepository.updateHabit(habit);
    }

    public static void deleteHabit(Habit habit) {
        habitRepository.deleteHabit(habit);
    }

    public static void deleteLastAddedHabit() {
        habitRepository.deleteLastAddedHabit();
    }

}
