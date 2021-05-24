package com.github.tharindusathis.goodhabits.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Toast;

import com.github.tharindusathis.goodhabits.R;
import com.github.tharindusathis.goodhabits.ui.habit.HabitBottomSheetFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private HabitBottomSheetFragment habitBottomSheetFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        habitBottomSheetFragment = new HabitBottomSheetFragment();
        CoordinatorLayout habitBottomSheetConstraintLayout = findViewById(R.id.habitBottomSheetConstraintLayout);
        BottomSheetBehavior<CoordinatorLayout> habitBottomSheetBehavior =
                BottomSheetBehavior.from(habitBottomSheetConstraintLayout);
        habitBottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);


        FloatingActionButton fab = findViewById(R.id.fab_add_habit);
        fab.setOnClickListener(view -> habitBottomSheetFragment.show(getSupportFragmentManager(), habitBottomSheetFragment.getTag()));

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_stats)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // preferences
        // initialize with default values.
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);
        // reading the changed settings value from shared preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String users_name = sharedPreferences.getString(SettingsActivity.KEY_PREF_USERS_NAME, null);
        if(users_name != null){
            Toast.makeText(this, String.format("Hello %s", users_name) , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this,
                    SettingsActivity.class);
//            intent.putExtra(EXTRA_MESSAGE, mOrderMessage);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}