package com.github.tharindusathis.goodhabits.ui.habit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.tharindusathis.goodhabits.ui.home.HomeViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.tharindusathis.goodhabits.R;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     HabitBottomSheetFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class HabitBottomSheetFragment extends BottomSheetDialogFragment {

    private HabitViewModel habitViewModel;
    private TextView textView;

    public HabitBottomSheetFragment() {
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View root = inflater.inflate(R.layout.fragment_habit_bottom_sheet, container, false);
        textView = root.findViewById(R.id.text_habits_btm_sheet);

        habitViewModel = new ViewModelProvider(requireActivity()).get(HabitViewModel.class);
        habitViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}