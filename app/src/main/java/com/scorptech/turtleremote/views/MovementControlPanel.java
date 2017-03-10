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
import android.widget.Toast;

import com.scorptech.turtleremote.R;
import com.scorptech.turtleremote.utils.CustomViewUtils;

/**
 * Created by talhahavadar on 01/03/2017.
 */

public class MovementControlPanel extends RelativeLayout implements View.OnTouchListener {

    private static final String TAG = "MovementControlPanel";

    private Joystick mJoystick;

    public MovementControlPanel(Context context) {
        super(context);
        initiate(R.mipmap.joystick_bg);
    }

    public MovementControlPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initiate(R.mipmap.joystick_bg);
    }

    public MovementControlPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initiate(R.mipmap.joystick_bg);
    }

    private void initiate(int resource) {
        initiateJoystick(resource);
        this.setOnTouchListener(this);
    }

    private void initiateJoystick(int resource) {
        if (mJoystick != null) {
            throw new RuntimeException(this.getClass().getName() + ": Joystick already initiated. You need to remove it before initiate again.");
        }
        mJoystick = new Joystick(this, resource);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int x = (int) event.getX();
        int y = (int)event.getY() < 0 ? 0 : (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (mJoystick.isMovable()) {
                    mJoystick.update(x, y);
                    Log.d(TAG, "onTouch: " + mJoystick.getPosition().toString() + " percentage: " + mJoystick.getPositionPercentage().toString() + " contianer: width=" + this.getWidth() + " height=" + this.getHeight());
                }
                break;
            case MotionEvent.ACTION_UP:
                mJoystick.setMovable(false);
                mJoystick.reset();
                Log.d(TAG, "onTouch: " + mJoystick.getPosition().toString() + " container: width=" + this.getWidth() + ", height=" + this.getHeight());
                break;
        }


        return true;
    }

    public class JoystickPosition {
        public int x;
        public int y;
        public JPosType type;

        @Override
        public String toString() {
            return "JPosition(x=" + x + ", y=" + y + ", type=" + type + ")";
        }
    }

    public enum JPosType {
        RELATIVE_PERCENTAGE,
        RELATIVE
    }

    public class Joystick implements OnTouchListener{
        private ImageView mView;
        private JoystickPosition position;
        private int _initialTop;
        private int _initialLeft;
        private int mWidth;
        private boolean mMovable;
        private View parent;

        public Joystick(RelativeLayout parent, int imageResource) {
            mView = new ImageView(parent.getContext());
            mView.setImageResource(imageResource);
            LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            mView.setLayoutParams(lp);
            mMovable = false;
            parent.addView(mView);
            this.parent = parent;
            position = new JoystickPosition();
            position.type = JPosType.RELATIVE;
            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mWidth = mView.getWidth();
                    if (_initialLeft == 0 && _initialTop == 0) {
                        _initialLeft = mView.getLeft() + mWidth/2;
                        _initialTop = mView.getTop() + mWidth/2;
                        position.x = _initialLeft;
                        position.y = _initialTop;
                    }
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });

            mView.setOnTouchListener(this);
        }

        public void update(int x, int y) {
            mView.setX(x-mWidth/2);
            position.x = mView.getX() < 0 ? 0 : x-mWidth/2;
            mView.setY(y-mWidth/2);
            position.y = mView.getY() < 0 ? 0 : y-mWidth/2;
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

        public JoystickPosition getPosition() {
            return position;
        }

        public JoystickPosition getPositionPercentage() {
            JoystickPosition pPosition = new JoystickPosition();
            pPosition.type = JPosType.RELATIVE_PERCENTAGE;
            pPosition.x = (int)(((float)position.x / (float)(parent.getWidth() - (mWidth / 2))) * 100.0);
            pPosition.y = (int)(((float)position.y / (float)(parent.getHeight() - (mWidth / 2))) * 100.0);

            // to restrict the value of positions between 100 and 0
            pPosition.x = pPosition.x > 100 ? 100 : pPosition.x < 0 ? 0 : pPosition.x;
            pPosition.y = pPosition.y > 100 ? 100 : pPosition.y < 0 ? 0 : pPosition.y;
            return pPosition;
        }
    }

}
