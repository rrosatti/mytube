package com.example.rodri.mytube.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by rodri on 6/20/2016.
 */
public class UtilMethods {

    public static void hideVirtualKeyboard(Activity activity) {

        View view = activity.getCurrentFocus();
        if (view != null ){
            InputMethodManager inputManager =
                    (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

}
