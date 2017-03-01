package com.scorptech.turtleremote.carsScreen;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.scorptech.turtleremote.MainActivity;
import com.scorptech.turtleremote.Pages;
import com.scorptech.turtleremote.R;
import com.scorptech.turtleremote.carsScreen.adapters.CarListAdapter;
import com.scorptech.turtleremote.models.Car;
import com.scorptech.turtleremote.mvp.MVPView;
import com.scorptech.turtleremote.mvp.Presenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A placeholder fragment containing a simple view.
 */
public class CarsView extends MVPView<CarsPresenter> implements ICarsView, AdapterView.OnItemClickListener {

    private static final String TAG = "CarsView";

    @BindView(R.id.carList)
    ListView lvCars;
    private Unbinder unbinder;

    public CarsView() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadCars();
        lvCars.setOnItemClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public CarsPresenter getPresenter() {
        return new CarsPresenter(this);
    }

    @Override
    public void loadCars() {
        CarListAdapter adapter = new CarListAdapter(getPresenter().getCars(), this.getContext());
        lvCars.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Car selected = ((CarListAdapter)lvCars.getAdapter()).getItem(i);
        ((MainActivity)getActivity()).goTo(Pages.CAR_MANAGEMENT); //TODO: need to send car as parameter
    }
}
