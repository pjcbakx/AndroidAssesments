package nl.avans.vincent.websocketsclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements WebSocket.OnLightSocketListener {
    View rootView;
    private static String TAG = "landscape";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        rootView = findViewById(R.id.main_root);

        OrientationEventListener listener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int orientation) {
                Log.i(TAG, ">>>>>" + orientation);
                if (orientation >= 0 && orientation <= 90) Log.i(TAG, "Normal");
                if (orientation >= 90 && orientation <= 180) Log.i(TAG, "Left");
                if (orientation >= 180 && orientation <= 270) Log.i(TAG, "Upside down");
                if (orientation >= 270 && orientation <= 380) Log.i(TAG, "Right");
            }
        };
        listener.enable();

        new WebSocket(this).connect();
    }

    @Override
    public void onColorReceived(int color) {
        rootView.setBackgroundColor(color);
    }

}
