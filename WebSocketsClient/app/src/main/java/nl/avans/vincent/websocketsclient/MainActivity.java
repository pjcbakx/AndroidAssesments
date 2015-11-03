package nl.avans.vincent.websocketsclient;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements WebSocket.OnLightSocketListener {
    View rootView;
    private static WebSocket webSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        rootView = findViewById(R.id.main_root);

        webSocket = new WebSocket(this);
        webSocket.connect();

        orientation();
    }

    @Override
    public void onColorReceived(int color, String text) {
        TextView tvMessage = (TextView) findViewById(R.id.text_message);
        tvMessage.setText(text);
        rootView.setBackgroundColor(color);
    }

    public void orientation() {

        if (getRotation() == 0) {
            TextView tvMessage = (TextView) findViewById(R.id.text_message);
            //tvMessage.setText("OK");

            webSocket.getSocket().emit("potrait");
        }
        if (getRotation() == 1 || getRotation() == 3) {
            TextView tvMessage = (TextView) findViewById(R.id.text_message);
            //tvMessage.setText("NIET OK!");

            webSocket.getSocket().emit("landscape");
        }

    }

    public int getRotation() {

        Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = display.getRotation();
        return rotation;

    }

}
