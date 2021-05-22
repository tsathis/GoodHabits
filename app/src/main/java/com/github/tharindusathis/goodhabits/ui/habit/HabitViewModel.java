package com.github.tharindusathis.goodhabits.ui.habit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HabitViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HabitViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Add a new habit.\n Here!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
