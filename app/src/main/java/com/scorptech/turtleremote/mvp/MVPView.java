package com.scorptech.turtleremote.mvp;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by talhahavadar on 19/12/2016.
 */

public abstract class MVPView<P extends Presenter> extends Fragment {

    public abstract P getPresenter();

}
