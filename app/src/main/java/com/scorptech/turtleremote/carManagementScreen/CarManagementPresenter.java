package com.scorptech.turtleremote.carManagementScreen;

import com.scorptech.turtleremote.carsScreen.CarsView;
import com.scorptech.turtleremote.carsScreen.ICarsView;
import com.scorptech.turtleremote.mvp.Presenter;

/**
 * Created by talhahavadar on 13/01/2017.
 */

public class CarManagementPresenter extends Presenter<CarManagementView> implements ICarManagementPresenter {

    CarManagementView mView;

    public CarManagementPresenter(CarManagementView view) {
        mView = view;
    }

    @Override
    public CarManagementView getView() {
        return mView;
    }
}
