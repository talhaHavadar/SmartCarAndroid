package com.scorptech.turtleremote.socket;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by talhahavadar on 14/01/2017.
 */

public class UDPClient extends Client {

    private static final String TAG = "UDPClient";

    public UDPClient(int port) {
        super("0.0.0.0", port);
    }

    @Override
    protected Void doInBackground(Object... voids) {
        DatagramSocket socket;
        socket = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            socket = new DatagramSocket(this.sourcePort, InetAddress.getByName(this.sourceAddress));
            int readed;
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            while (true) {
                socket.receive(packet);
                baos.write(packet.getData(), 0, packet.getData().length);
                if (mListener != null) {
                    mListener.onData(this, new String(packet.getData(), 0, packet.getLength()));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }

        return null;
    }

    @Override
    public void send(final String data, final String toServer,final int port) {
        final byte[] dataBytes = data.getBytes();

        AsyncTask task = new AsyncTask<Object, Void, Void>() {
            @Override
            protected Void doInBackground(Object... voids) {
                DatagramSocket socket = null;
                try {
                    socket = new DatagramSocket();
                    DatagramPacket packet = new DatagramPacket(dataBytes, dataBytes.length, InetAddress.getByName(toServer), port);
                    socket.send(packet);
                    Log.d(TAG, "send(to" + toServer + "): " + data);
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (socket != null)
                        socket.close();
                }

                return null;
            }
        };

        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }
}
