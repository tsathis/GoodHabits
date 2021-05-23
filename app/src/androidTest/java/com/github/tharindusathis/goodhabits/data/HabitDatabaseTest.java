package com.github.tharindusathis.goodhabits.data;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.tharindusathis.goodhabits.model.Habit;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class HabitDatabaseTest {
    private HabitDao habitDao;
    private HabitDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, HabitDatabase.class).build();
        habitDao = db.habitDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void writeUserAndReadInList() {
        Habit habit = new Habit();
        habit.setTitle("A test habit");
        habit.setCreatedDate(new Date());
        habit.setStartedAt(new Date());

        habitDao.insert(habit);
        LiveData<List<Habit>> habits = habitDao.getAll();
        habits.observeForever(habitList -> {
            assertNotNull(habitList.get(0));
            assertEquals(habitList.get(0), habit);
        });
    }
}