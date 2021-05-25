package com.github.tharindusathis.goodhabits.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.github.tharindusathis.goodhabits.model.Habit;
import com.github.tharindusathis.goodhabits.util.TypeConverterUtil;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Habit.class}, version = 1, exportSchema = false)
@TypeConverters({TypeConverterUtil.class})
public abstract class HabitDatabase extends RoomDatabase {

    public abstract HabitDao habitDao();

    private static volatile HabitDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static HabitDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (HabitDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            HabitDatabase.class, "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // clean state
            databaseWriteExecutor.execute(() -> {

                // Populate the database in the background.
                HabitDao dao = INSTANCE.habitDao();
                dao.deleteAll();
                // todo: remove [DEMO ONLY]
                dao.insert(new Habit("Get up before 6", new Date(System.currentTimeMillis() - (long) 5 * 3600 * 1000)));
                dao.insert(new Habit("Meditate twice a day \uD83E\uDDD8", new Date(System.currentTimeMillis() - (long) 10 * 24 * 3600 * 1000)));
                dao.insert(new Habit("\uD83D\uDE34 sleep before 10", new Date(System.currentTimeMillis() - (long) 30 * 24 * 3600 * 1000)));
                dao.insert(new Habit("Read a book daily", new Date(System.currentTimeMillis() - (long) 2 * 24 * 3600 * 1000)));
            });
        }
    };


}
