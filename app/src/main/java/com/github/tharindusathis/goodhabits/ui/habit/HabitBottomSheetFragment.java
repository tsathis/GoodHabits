package com.github.tharindusathis.goodhabits.ui.habit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.github.tharindusathis.goodhabits.model.Habit;
import com.github.tharindusathis.goodhabits.model.HabitAndroidViewModel;
import com.github.tharindusathis.goodhabits.util.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.github.tharindusathis.goodhabits.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     HabitBottomSheetFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class HabitBottomSheetFragment extends BottomSheetDialogFragment {

    private HabitViewModel habitViewModel;

    private EditText editTextHabit;
    private ImageButton buttonSaveHabit;
    private ImageButton buttonDiscard;

    public HabitBottomSheetFragment() {
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View root = inflater.inflate(R.layout.fragment_habit_bottom_sheet, container, false);

        editTextHabit = root.findViewById(R.id.edit_text_habit_bottom_sheet);
        buttonSaveHabit = root.findViewById(R.id.button_save_bottom_sheet);
        buttonDiscard = root.findViewById(R.id.button_discard_bottom_sheet);

        habitViewModel = new ViewModelProvider(requireActivity()).get(HabitViewModel.class);

        /*
        private TextView textViewHabit;
        textViewHabit = root.findViewById(R.id.text_habits_bottom_sheet);
        habitViewModel.getText().observe(getViewLifecycleOwner(), s -> textViewHabit.setText(s));
        */

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonSaveHabit.setOnClickListener(v -> {
            String title = editTextHabit.getText().toString();
            if (!TextUtils.isEmpty(title)) {
                Habit newHabit = new Habit(title, new Date());
                HabitAndroidViewModel.insertHabit(newHabit);
                Utils.hideSoftKeyboard(v);
                if (this.isVisible()) {
                    this.dismiss();
                }

                Snackbar
                        .make(requireActivity().findViewById(R.id.fab_add_habit),
                                R.string.habit_added_notification, Snackbar.LENGTH_SHORT)
                        .setAction(R.string.habit_added_action_text, null)
                        .show();

            }
        });

        buttonDiscard.setOnClickListener(v -> {
            this.dismiss();
            discard();
        });

    }

    private void discard() {
        editTextHabit.setText("");
    }

}