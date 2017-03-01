package com.scorptech.turtleremote.carsScreen;

import com.scorptech.turtleremote.models.Car;
import com.scorptech.turtleremote.mvp.Presenter;

import java.util.ArrayList;

/**
 * Created by talhahavadar on 19/12/2016.
 */

public class CarsPresenter extends Presenter<CarsView> implements ICarsPresenter {

    private CarsView mView;

    public CarsPresenter(CarsView view) {
        mView = view;
    }

    @Override
    public CarsView getView() {
        return mView;
    }


    @Override
    public ArrayList<Car> getCars() {
        Car car = new Car();
        car.id = 1;
        ArrayList<Car> cars = new ArrayList<>();
        cars.add(car);
        return cars;
    }
}
