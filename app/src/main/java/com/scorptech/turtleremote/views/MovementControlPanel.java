package com.scorptech.turtleremote.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.scorptech.turtleremote.utils.CustomViewUtils;

/**
 * Created by talhahavadar on 01/03/2017.
 */

public class MovementControlPanel extends RelativeLayout implements View.OnTouchListener {

    private static final String TAG = "MovementControlPanel";

    private Joystick mJoystick;

    public MovementControlPanel(Context context) {
        super(context);
        initiate(100);
    }

    public MovementControlPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initiate(100);
    }

    public MovementControlPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initiate(100);
    }

    private void initiate(int width) {
        initiateJoystick(width);
        this.setOnTouchListener(this);
    }

    private void initiateJoystick(int width) {
        if (mJoystick != null) {
            throw new RuntimeException(this.getClass().getName() + ": Joystick already initiated. You need to remove it before initiate again.");
        }
        mJoystick = new Joystick(this, width);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int x = (int) event.getX();
        int y = (int)event.getY() < 0 ? 0 : (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (mJoystick.isMovable()) {
                    mJoystick.update(x, y);
                }
                break;
            case MotionEvent.ACTION_UP:
                mJoystick.setMovable(false);
                mJoystick.reset();
                break;
        }


        return true;
    }

    public class Joystick implements OnTouchListener{
        private ImageView mView;
        private int x;
        private int y;
        private int _initialTop;
        private int _initialLeft;
        private int mWidth;
        private boolean mMovable;


        public Joystick(RelativeLayout parent, int width) {
            mView = new ImageView(parent.getContext());
            mView.setBackgroundColor(Color.WHITE);
            mWidth = width;
            LayoutParams lp = new LayoutParams(width, width);
            lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            mView.setLayoutParams(lp);
            mMovable = false;
            parent.addView(mView);

            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (_initialLeft == 0 && _initialTop == 0) {
                        _initialLeft = mView.getLeft() + mWidth/2;
                        _initialTop = mView.getTop() + mWidth/2;
                    }
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });

            mView.setOnTouchListener(this);
        }

        public void update(int x, int y) {
            mView.setX(x-mWidth/2);
            mView.setY(y-mWidth/2);
        }

        public void reset() {
            this.update(_initialLeft, _initialTop);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mMovable = true;
            }
            return false;
        }

        private void setMovable(boolean movable) {
            mMovable = movable;
        }

        public boolean isMovable() {
            return mMovable;
        }
    }

}
