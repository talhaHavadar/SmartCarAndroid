package com.scorptech.turtleremote.socket;

import android.os.AsyncTask;

/**
 * Created by talhahavadar on 14/01/2017.
 */

public abstract class Client extends AsyncTask<Object, Void, Void> {

    SocketListener mListener;
    String sourceAddress;
    int sourcePort;

    public Client(String sourceAddress, int sourcePort) {
        this.sourceAddress = sourceAddress;
        this.sourcePort = sourcePort;
    }

    @Override
    protected abstract Void doInBackground(Object... voids);

    public void setSocketListener(SocketListener listener) {
        this.mListener = listener;
    }

    public abstract void send(String data, String toServer, int port);

}
