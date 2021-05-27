package com.github.tharindusathis.goodhabits.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.github.tharindusathis.goodhabits.R;
import com.github.tharindusathis.goodhabits.ui.habit.HabitBottomSheetFragment;
import com.github.tharindusathis.goodhabits.ui.habit.HabitViewModel;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import static com.github.tharindusathis.goodhabits.util.Utils.setThemeNightMode;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ACTIVITY_MAIN";

    private AppBarConfiguration mAppBarConfiguration;
    FloatingActionButton fab;
    Snackbar snackbar;
    private HabitBottomSheetFragment habitBottomSheetFragment;

    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest, signUpRequest;
    ActivityResultLauncher<IntentSenderRequest> signInResultHandler;

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

        new ViewModelProvider(this)
                .get(HabitViewModel.class)
                .setHabitBottomSheetFragment(habitBottomSheetFragment);

        fab = findViewById(R.id.fab_add_habit);
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
        String usersName = sharedPreferences.getString(SettingsActivity.KEY_PREF_USERS_NAME, null);
        TextView textViewSmallNavHeader = navigationView.getHeaderView(0).findViewById(R.id.text_view_small_nav_header);
        if (usersName != null && usersName.trim().length() > 0) {
            textViewSmallNavHeader.setText(String.format("Signed in as %s", usersName));
            textViewSmallNavHeader.setVisibility(View.VISIBLE);
        }else {
            textViewSmallNavHeader.setVisibility(View.GONE);
        }

        final String[] prefThemeValues = getResources().getStringArray(R.array.theme_values);
        String prefTheme = sharedPreferences.getString(getString(R.string.pref_theme), prefThemeValues[0]);
        setThemeNightMode(prefThemeValues, prefTheme);

        oneTapClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(getString(R.string.your_web_client_id))
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                .build();
        signUpRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(false)
                        .setServerClientId(getString(R.string.your_web_client_id))
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                .build();
        signInResultHandler = registerForActivityResult(
                new ActivityResultContracts.StartIntentSenderForResult(), result -> {
                    if(result.getResultCode() == RESULT_OK){
                        Log.d(TAG, "RESULT_OK");
                    }else if(result.getResultCode() == RESULT_CANCELED){
                        Log.d(TAG, "RESULT_CANCELED");
                    }else if(result.getResultCode() == RESULT_FIRST_USER) {
                        Log.d(TAG, "RESULT_FIRST_USER");
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this,
                    SettingsActivity.class);
            // intent.putExtra(EXTRA_MESSAGE, mOrderMessage);
            startActivity(intent);
            return true;
        }else if(itemId == R.id.account) {
            oneTapClient
                    .beginSignIn(signUpRequest)
                    .addOnSuccessListener(this, result ->
                            signInResultHandler.launch(
                                    new IntentSenderRequest
                                            .Builder(result.getPendingIntent())
                                            .build()
                            )
                    )
                    .addOnFailureListener(this, exception -> {
                        Log.d(TAG, exception.getLocalizedMessage());
                        snackbar = Snackbar.make(fab, "SignIn Error! " + exception.getLocalizedMessage(),
                                BaseTransientBottomBar.LENGTH_INDEFINITE)
                                .setAction(R.string.dimiss_action_text, v -> dismissSnackbar());
                        snackbar.show();
                    });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void dismissSnackbar() {
        snackbar.dismiss();
    }
}