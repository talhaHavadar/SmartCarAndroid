package com.scorptech.turtleremote.carManagementScreen;

import com.github.niqdev.mjpeg.MjpegInputStream;

/**
 * Created by talhahavadar on 13/01/2017.
 */

public interface ICarManagementView {

    void forward(int power);
    void steering(int angle);
    void backward(int power);
    void mjpegConnectionSuccess(MjpegInputStream inputStream);
    void mjpegConnectionError(Throwable throwable);
    void toast(String message);

}
