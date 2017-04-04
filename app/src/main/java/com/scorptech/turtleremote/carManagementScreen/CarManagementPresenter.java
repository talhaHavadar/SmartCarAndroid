package com.scorptech.turtleremote.carManagementScreen;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.github.niqdev.mjpeg.DisplayMode;
import com.github.niqdev.mjpeg.Mjpeg;
import com.github.niqdev.mjpeg.MjpegInputStream;
import com.scorptech.turtleremote.carsScreen.CarsView;
import com.scorptech.turtleremote.carsScreen.ICarsView;
import com.scorptech.turtleremote.mvp.Presenter;
import com.scorptech.turtleremote.socket.Client;
import com.scorptech.turtleremote.socket.CommandBuilder;
import com.scorptech.turtleremote.socket.SocketListener;
import com.scorptech.turtleremote.socket.UDPClient;
import com.scorptech.turtleremote.views.MovementControlPanel;

import java.util.regex.Pattern;

import rx.functions.Action1;

/**
 * Created by talhahavadar on 13/01/2017.
 */

public class CarManagementPresenter extends Presenter<CarManagementView> implements ICarManagementPresenter {

    CarManagementView mView;
    UDPClient client;

    public CarManagementPresenter(CarManagementView view) {
        mView = view;
        client= new UDPClient(7777);
    }

    @Override
    public CarManagementView getView() {
        return mView;
    }

    @Override
    public void setupSocketConnection() {
        client.setSocketListener(new SocketListener() {
            @Override
            public void onData(Client client, final String data) {
                if (getView().getActivity() != null) {
                    getView().getActivity().runOnUiThread(new Runnable() {
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
    }

    @Override
    public void setupMjpegConnection() {
        Mjpeg.newInstance().open("http://192.168.1.1:8080/?action=stream", 5)
                .subscribe(new Action1<MjpegInputStream>() {
                    @Override
                    public void call(MjpegInputStream mjpegInputStream) {
                        getView().mjpegConnectionSuccess(mjpegInputStream);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(getClass().getSimpleName(), "mjpeg error", throwable);
                        getView().mjpegConnectionError(throwable);
                    }
                });
    }

    @Override
    public void evaluateJoystick(MovementControlPanel.Joystick joystick) {

        MovementControlPanel.JoystickPosition jPosition = joystick.getPositionPercentage();
        CommandBuilder.Command command = new CommandBuilder()
                .setType(CommandBuilder.CommandType.JOYSTICK)
                .addArgument("" + jPosition.x)
                .addArgument("" + jPosition.y)
                .build();
        client.send(command.getCommandString(), "192.168.1.3", 4444);
        client.send(joystick.getResetPosition(MovementControlPanel.JPosType.RELATIVE_PERCENTAGE).toString(), "192.168.1.3", 4444);
    }
}
