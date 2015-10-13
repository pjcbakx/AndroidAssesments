package nl.avans.vincent.websocketsclient;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
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
    private static final String BASE_URL = "http://coolsma.synology.me:3003";
    //private static final String TAG = "";

    private Socket socket;
    private OnLightSocketListener listener;

    public WebSocket(OnLightSocketListener listener) {
        this.listener = listener;
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

                final int color = Color.argb(a, r, g, b);

                runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onColorReceived(color);
                    }
                });

            } catch (Exception e) {
                Log.e(LOG_TAG, "Error getting result from JSON", e);
            }
        }
    }

    private void runOnMainThread(Runnable runnable) {
        // Create handler for the Main Looper and execute code on Main Looper
        Handler handler = new android.os.Handler(Looper.getMainLooper());
        handler.post(runnable);
    }

    interface OnLightSocketListener {
        void onColorReceived(int color);
    }
/*
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "landscape");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "portrait");
        }
    }
*/
}
