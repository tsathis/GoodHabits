package com.github.tharindusathis.goodhabits.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.github.tharindusathis.goodhabits.model.Habit;

import java.util.List;

@Dao
public interface HabitDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Habit habit);

    @Query("SELECT * FROM habit_table ORDER BY habit_table.created_at DESC")
    LiveData<List<Habit>> getAll();

    @Query("SELECT * FROM habit_table WHERE habit_table.habit_id == :habitId")
    LiveData<Habit> get(long habitId);

    @Query("SELECT COUNT(habit_id) FROM habit_table")
    LiveData<Long> count();

    @Query("SELECT * FROM habit_table WHERE habit_table.started_at == (SELECT MIN(habit_table.started_at) FROM habit_table)")
    LiveData<Habit> getFirstStarted();

    @Update
    void update(Habit habit);

    @Delete
    void delete(Habit habit);

    @Query("DELETE FROM habit_table")
    void deleteAll();

    @Query("DELETE FROM habit_table WHERE habit_table.created_at == (SELECT MAX(habit_table.created_at) FROM habit_table)")
    void deleteLastAdded();
}
