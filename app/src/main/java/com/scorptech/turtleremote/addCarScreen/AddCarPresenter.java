package com.scorptech.turtleremote.addCarScreen;

import com.scorptech.turtleremote.models.Car;
import com.scorptech.turtleremote.mvp.Presenter;

import java.util.ArrayList;

/**
 * Created by talhahavadar on 20/12/2016.
 */

public class AddCarPresenter extends Presenter<AddCarView> implements IAddCarPresenter {

    AddCarView mView;

    public AddCarPresenter(AddCarView view) {
        mView = view;
    }

    @Override
    public AddCarView getView() {
        return mView;
    }

    @Override
    public ArrayList<Car> getCars() {
        ArrayList<Car> cars = new ArrayList<>();
        Car car = new Car();
        car.id = 1;
        cars.add(car);
        return cars;
    }
}
