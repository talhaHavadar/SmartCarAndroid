package com.scorptech.turtleremote.mvp;

/**
 * Created by talhahavadar on 19/12/2016.
 */

public abstract class Presenter<V extends MVPView> {
    public abstract V getView();
}
