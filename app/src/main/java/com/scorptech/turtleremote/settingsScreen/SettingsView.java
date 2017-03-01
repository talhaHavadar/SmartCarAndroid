package com.scorptech.turtleremote.settingsScreen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scorptech.turtleremote.R;
import com.scorptech.turtleremote.mvp.MVPView;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsView extends MVPView<SettingsPresenter> implements ISettingsView {

    private static final String TAG = "SettingsView";

    public SettingsView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings_view, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public SettingsPresenter getPresenter() {
        return new SettingsPresenter(this);
    }
}
