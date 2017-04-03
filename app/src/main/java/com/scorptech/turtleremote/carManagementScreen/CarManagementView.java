package com.scorptech.turtleremote.carManagementScreen;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.niqdev.mjpeg.DisplayMode;
import com.github.niqdev.mjpeg.Mjpeg;
import com.github.niqdev.mjpeg.MjpegInputStream;
import com.github.niqdev.mjpeg.MjpegSurfaceView;
import com.scorptech.turtleremote.R;
import com.scorptech.turtleremote.mvp.MVPView;
import com.scorptech.turtleremote.socket.UDPClient;
import com.scorptech.turtleremote.socket.Client;
import com.scorptech.turtleremote.socket.SocketListener;
import com.scorptech.turtleremote.views.JoystickListener;
import com.scorptech.turtleremote.views.MovementControlPanel;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.functions.Action1;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarManagementView extends MVPView<CarManagementPresenter> implements ICarManagementView, SeekBar.OnSeekBarChangeListener {

    @BindView(R.id.mjpgView)
    MjpegSurfaceView mjpgView;
    @BindView(R.id.controlPanelContainer)
    MovementControlPanel movementControlPanel;
    private Unbinder unbinder;
    @BindView(R.id.controlPanelContainer)
    MovementControlPanel movementPanel;


    UDPClient client;

    public CarManagementView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_car_management_view, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getPresenter().setupMjpegConnection();
        getPresenter().setupSocketConnection();
        movementPanel.setJoystickListener(new JoystickListener() {
            @Override
            public void OnTouch(MovementControlPanel.Joystick joystick) {
                Log.d(TAG, "OnTouch(JoystickListener): " + joystick.getPositionPercentage().toString());
            }
        });
    }

    @Override
    public CarManagementPresenter getPresenter() {
        return new CarManagementPresenter(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        client.send("steering|"+i, "192.168.1.1", 7777);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void forward(int power) {

    }

    @Override
    public void steering(int angle) {

    }

    @Override
    public void backward(int power) {

    }

    @Override
    public void mjpegConnectionSuccess(MjpegInputStream inputStream) {
        mjpgView.setSource(inputStream);
        mjpgView.setDisplayMode(DisplayMode.FULLSCREEN);
        mjpgView.showFps(true);
    }

    @Override
    public void mjpegConnectionError(Throwable throwable) {
        Toast.makeText(CarManagementView.this.getContext(), "Error: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
    }
}
