package nl.avans.vincent.websocketsclient;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by Vincent on 13-10-2015.
 */
public class WebSocket {
    private static final String LOG_TAG = "WebSocket";
    private static final String BASE_URL = "http://192.168.0.14:3003";
    //private static final String TAG = "";

    private Socket socket;
    private int color;
    //private OnLightSocketListener listener;

    public WebSocket() {
      //  this.listener = listener;
        try {
            socket = IO.socket(BASE_URL);
        } catch (URISyntaxException e) {
            Log.e(LOG_TAG, "Error initializing socket, URI invalid", e);
        }

    }

    public void connect() {
        if (socket != null) {
            // Register events and connect

            socket.on("bgcolor", new colorBroadcastListener());
            socket.connect();
        }
    }

    public void disconnect() {
        if (socket != null) {
            // Disconnect and unregister events
            socket.disconnect();
            socket.off();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public int getColor() {
        return color;
    }

    private class colorBroadcastListener implements Emitter.Listener {
        @Override
        public void call(Object... args) {

            try {
                // Parse JSON and get result
                JSONObject response = (JSONObject) args[0];
                int r = response.getInt("r");
                int g = response.getInt("g");
                int b = response.getInt("b");
                int a = response.getInt("a");

                color = Color.argb(a, r, g, b);

                /*
                runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onColorReceived(color);
                    }
                });
                */

            } catch (Exception e) {
                Log.e(LOG_TAG, "Error getting result from JSON", e);
            }
        }
    }

    /*
    private void runOnMainThread(Runnable runnable) {
        // Create handler for the Main Looper and execute code on Main Looper
        Handler handler = new android.os.Handler(Looper.getMainLooper());
        handler.post(runnable);
    }

    interface OnLightSocketListener {
        void onColorReceived(int color);
    }
    */
}
