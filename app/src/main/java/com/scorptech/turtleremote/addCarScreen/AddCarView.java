package com.scorptech.turtleremote.addCarScreen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.scorptech.turtleremote.R;
import com.scorptech.turtleremote.addCarScreen.adapters.AddCarListAdapter;
import com.scorptech.turtleremote.mvp.MVPView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCarView extends MVPView<AddCarPresenter> implements IAddCarView {

    @BindView(R.id.addCarList)
    ListView lvAddCarList;


    public AddCarView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_car_view, container, false);
        ButterKnife.bind(this, rootView);
        loadCars();
        return rootView;
    }

    @Override
    public AddCarPresenter getPresenter() {
        return new AddCarPresenter(this);
    }

    @Override
    public void loadCars() {
        AddCarListAdapter adapter = new AddCarListAdapter(getPresenter().getCars(), getContext());
        lvAddCarList.setAdapter(adapter);
    }
}
