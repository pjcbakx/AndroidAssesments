package nl.avans.vincent.websocketsclient;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by Vincent on 26-10-2015.
 */
public class OrientationActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getRotation() == 0) {
            View rootView = findViewById(R.id.main_root);
            rootView.setBackgroundColor(WebSocketSingleton.getWebSocket().getColor());

            TextView tvMessage = (TextView) findViewById(R.id.text_message);
            tvMessage.setText("OK");

            WebSocketSingleton.getWebSocket().getSocket().emit("potrait");
        }
        if (getRotation() == 3) {
            View rootView = findViewById(R.id.main_root);
            rootView.setBackgroundColor(WebSocketSingleton.getWebSocket().getColor());

            TextView tvMessage = (TextView) findViewById(R.id.text_message);
            tvMessage.setText("NIET OK!");

            WebSocketSingleton.getWebSocket().getSocket().emit("landscape");
        }
    }

    public int getRotation() {
        Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = display.getRotation();
        return rotation;
    }

}