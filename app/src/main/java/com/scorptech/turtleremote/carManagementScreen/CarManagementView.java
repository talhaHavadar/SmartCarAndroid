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

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarManagementView extends MVPView<CarManagementPresenter> implements ICarManagementView, SeekBar.OnSeekBarChangeListener {

    @BindView(R.id.mjpgView)
    MjpegSurfaceView mjpgView;
    private Unbinder unbinder;


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

        client= new UDPClient(7777);
        client.setSocketListener(new SocketListener() {
            @Override
            public void onData(Client client, final String data) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String[] splittedResponse = data.split(Pattern.quote("|"));
                            String command = splittedResponse[0];
                            if (command.equalsIgnoreCase("dist_front")) {
                                String val = splittedResponse[1];
                            }
                        }
                    });
                }

            }
        });
        client.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        // Mjpeg.newInstance().open("http://192.168.1.1:8081", 5)
        Mjpeg.newInstance().open("http://192.168.1.12:8080?action=stream", 5)
                .subscribe(new Action1<MjpegInputStream>() {
                    @Override
                    public void call(MjpegInputStream mjpegInputStream) {
                        mjpgView.setSource(mjpegInputStream);
                        mjpgView.setDisplayMode(DisplayMode.FULLSCREEN);
                        mjpgView.showFps(true);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(getClass().getSimpleName(), "mjpeg error", throwable);
                        Toast.makeText(CarManagementView.this.getContext(), "Error: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
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
}
