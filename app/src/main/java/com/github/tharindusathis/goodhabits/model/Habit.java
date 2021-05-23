package com.github.tharindusathis.goodhabits.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Entity(tableName = "habit_table")
public class Habit {
    @ColumnInfo(name = "habit_id")
    @PrimaryKey(autoGenerate = true)
    public long habitId;

    @NonNull
    public String title;

    @ColumnInfo(name = "started_at")
    public Date startedAt;

    @ColumnInfo(name = "created_at")
    public Date createdDate;

    /**
     * @deprecated
     */
    public Habit(@NotNull String title, Date startedAt, Date createdDate) {
        this.title = title;
        this.startedAt = startedAt;
        this.createdDate = createdDate;
    }

    public Habit(@NotNull String title, Date startedAt) {
        this.title = title;
        this.startedAt = startedAt;
        this.createdDate = new Date();
    }

    public Habit() {
    }

    public long getHabitId() {
        return habitId;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @NotNull
    @Override
    public String toString() {
        return "Habit{" +
                "habitId=" + habitId +
                ", title='" + title + '\'' +
                ", startedAt=" + startedAt +
                ", createdDate=" + createdDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Habit habit = (Habit) o;

        if (!title.equals(habit.title)) return false;
        if (!startedAt.equals(habit.startedAt)) return false;
        return createdDate.equals(habit.createdDate);
    }

}
