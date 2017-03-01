package com.scorptech.turtleremote.settingsScreen;

import com.scorptech.turtleremote.mvp.MVPView;
import com.scorptech.turtleremote.mvp.Presenter;

/**
 * Created by talhahavadar on 19/12/2016.
 */

public class SettingsPresenter extends Presenter<SettingsView> {

    SettingsView mView;

    public SettingsPresenter(SettingsView view) {
        mView = view;
    }

    @Override
    public SettingsView getView() {
        return mView;
    }
}
