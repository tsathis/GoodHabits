package com.github.tharindusathis.goodhabits.ui.habit;

import android.content.DialogInterface;
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
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.github.tharindusathis.goodhabits.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    private CalendarView calendarView;
    private Chip chipChoiceNow, chipChoiceYesterday, chipChoiceLastWeek, chipChoiceCalendar;
    private List<View> groupEditStartedAt;

    Calendar calendar = Calendar.getInstance();
    private Date dateStartedAt;

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
        calendarView = root.findViewById(R.id.calendar_view);
        groupEditStartedAt = Arrays.asList(
                calendarView,
                root.findViewById(R.id.label_starting_at_input),
                root.findViewById(R.id.scroll_view_starting_at)
        );


        chipChoiceNow = root.findViewById(R.id.chip_choice_now);
        chipChoiceYesterday = root.findViewById(R.id.chip_choice_yesterday);
        chipChoiceLastWeek = root.findViewById(R.id.chip_choice_last_week);
        chipChoiceCalendar = root.findViewById(R.id.chip_choice_calendar);

        habitViewModel = new ViewModelProvider(requireActivity()).get(HabitViewModel.class);

        return root;
    }

    private void reset() {
        for (View view : groupEditStartedAt) {
            view.setVisibility(View.VISIBLE);
        }
        calendarView.setVisibility(View.GONE);
        dateStartedAt = new Date();
        editTextHabit.setText("");
        editTextHabit.setError(null);
        habitViewModel.setHabit(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (habitViewModel.getSelectedHabit().getValue() != null) {
            Habit editingHabit = habitViewModel.getSelectedHabit().getValue();
            editTextHabit.setText(editingHabit.getTitle());
            for (View view : groupEditStartedAt) {
                view.setVisibility(View.GONE);
            }
        }else{
            reset();
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonSaveHabit.setOnClickListener(v -> {
            String title = editTextHabit.getText().toString().trim();
            if (!TextUtils.isEmpty(title)) {
                if (!habitViewModel.isEditing()) {
                    Habit newHabit = new Habit(title, dateStartedAt);
                    HabitAndroidViewModel.insertHabit(newHabit);
                    Snackbar
                            .make(requireActivity().findViewById(R.id.fab_add_habit),
                                    R.string.habit_added_notification, Snackbar.LENGTH_LONG)
                            .setAction(R.string.undo_action_text, v_ -> HabitAndroidViewModel.deleteLastAddedHabit())
                            .show();
                } else {
                    Habit editingHabit = habitViewModel.getSelectedHabit().getValue();
                    Objects.requireNonNull(editingHabit).setTitle(title);
                    HabitAndroidViewModel.updateHabit(editingHabit);
                    Snackbar
                            .make(requireActivity().findViewById(R.id.fab_add_habit),
                                    R.string.habit_updated_notification, Snackbar.LENGTH_LONG)
                            .show();
                }
                Utils.hideSoftKeyboard(v);
                if (this.isVisible()) {
                    this.dismiss();
                }
                reset();
            } else {
                editTextHabit.requestFocus();
            }
        });

        buttonDiscard.setOnClickListener(v -> {
            this.dismiss();
            reset();
        });

        calendarView.setOnDateChangeListener((view_, year, month, dayOfMonth) -> {
            calendar.clear();
            calendar.set(year, month, dayOfMonth);
            dateStartedAt = calendar.getTime();
        });

        View.OnClickListener startedAtChoiceChipOnClickListener = v -> {
            Utils.hideSoftKeyboard(v);
            calendar.setTime(dateStartedAt);

            int itemId = v.getId();
            if (itemId == R.id.chip_choice_now) {
                calendar.add(Calendar.DAY_OF_YEAR, 0);
            }else if (itemId == R.id.chip_choice_yesterday) {
                calendar.add(Calendar.DAY_OF_YEAR, -1);
            }else if (itemId == R.id.chip_choice_last_week) {
                calendar.add(Calendar.DAY_OF_YEAR, -7);
            }else if (itemId == R.id.chip_choice_calendar) {
                calendarView.setVisibility(View.VISIBLE);
            }
            dateStartedAt = calendar.getTime();
            calendarView.setDate(dateStartedAt.getTime());
        };

        chipChoiceNow.setOnClickListener(startedAtChoiceChipOnClickListener);
        chipChoiceYesterday.setOnClickListener(startedAtChoiceChipOnClickListener);
        chipChoiceLastWeek.setOnClickListener(startedAtChoiceChipOnClickListener);
        chipChoiceCalendar.setOnClickListener(startedAtChoiceChipOnClickListener);
    }

    @Override
    public void onDismiss(@NonNull @NotNull DialogInterface dialog) {
        super.onDismiss(dialog);
        reset();
    }
}