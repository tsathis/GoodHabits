package com.github.tharindusathis.goodhabits.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public final class Utils {

    private Utils() {
    }

    public static void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
