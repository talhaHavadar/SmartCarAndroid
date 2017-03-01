package com.scorptech.turtleremote.socket;

/**
 * Created by talhahavadar on 14/01/2017.
 */

public interface SocketListener {

    void onData(Client client, String data);

}
