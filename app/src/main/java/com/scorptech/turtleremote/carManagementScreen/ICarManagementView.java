package com.scorptech.turtleremote.carManagementScreen;

/**
 * Created by talhahavadar on 13/01/2017.
 */

public interface ICarManagementView {

    void forward(int power);
    void steering(int angle);
    void backward(int power);
}