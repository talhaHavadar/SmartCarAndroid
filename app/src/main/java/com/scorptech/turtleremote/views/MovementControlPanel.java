package com.scorptech.turtleremote.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by talhahavadar on 01/03/2017.
 */

public class MovementControlPanel extends RelativeLayout {

    private static final String TAG = "MovementControlPanel";

    private Joystick mJoystick;

    public MovementControlPanel(Context context) {
        super(context);
        initiateJoystick();
    }

    public MovementControlPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initiateJoystick();
    }

    public MovementControlPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initiateJoystick();
    }

    private void initiateJoystick() {
        if (mJoystick != null) {
            throw new RuntimeException(this.getClass().getName() + ": Joystick already initiated. You need to remove it before initiate again.");
        }
        mJoystick = new Joystick(this);
    }

    public class Joystick {
        private ImageView mView;

        public Joystick(RelativeLayout parent) {
            mView = new ImageView(parent.getContext());
            mView.setBackgroundColor(Color.WHITE);
            LayoutParams lp = new LayoutParams(80, 80);
            lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            mView.setLayoutParams(lp);
            parent.addView(mView);
            int[] location = new int[2];
            mView.getLocationOnScreen(location);
            Log.d(TAG, "Joystick: " + location.toString());

        }


    }

}
