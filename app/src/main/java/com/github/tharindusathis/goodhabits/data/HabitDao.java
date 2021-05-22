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

    @Query(("SELECT * FROM habit_table"))
    LiveData<List<Habit>> getAll();

    @Query(("SELECT * FROM habit_table WHERE habit_table.habit_id == :habitId"))
    LiveData<Habit> get(long habitId);

    @Update
    void update(Habit habit);

    @Delete
    void delete(Habit habit);

    @Query("DELETE FROM habit_table")
    void deleteAll();
}
