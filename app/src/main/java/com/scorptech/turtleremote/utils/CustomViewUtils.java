package com.scorptech.turtleremote.utils;

import android.view.View;

/**
 * Created by t.havadar on 01-Mar-17.
 */

public class CustomViewUtils {

    public static int getRelativeLeft(View view) {
        if(view.getParent() == view.getRootView())
            return view.getLeft();
        else
            return view.getLeft() + getRelativeLeft((View) view.getParent());
    }

    public static int getRelativeTop(View view) {
        if (view.getParent() == view.getRootView())
            return view.getTop();
        else
            return view.getTop() + getRelativeTop((View) view.getParent());
    }

}
