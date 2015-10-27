package nl.avans.vincent.websocketsclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    View rootView;
    private static String TAG = "landscape";
    private static WebSocket webSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addListenerOnButton();

        //Makes new thread for the background and text
        //Thread thread = new Thread(new Runnable() {
        //    @Override
        //    public void run() {
        //        runOnMainThread();
        //    }
        //});
        //thread.start();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        rootView = findViewById(R.id.main_root);

        webSocket = new WebSocket();
        webSocket.connect();

    }

    /*
    @Override
    public void onColorReceived(int color) {
        rootView.setBackgroundColor(color);
    }
    */

    private void runOnMainThread() {
        // Create handler for the Main Looper and execute code on Main Looper
        //Handler handler = new android.os.Handler(Looper.getMainLooper());
        //handler.post(runnable);

       // WebSocketSingleton.setWebSocket(webSocket);
       // Intent intent = new Intent(getApplicationContext(), OrientationActivity.class);
       // startActivity(intent);
    }


    public void addListenerOnButton() {
        final Button button = (Button) findViewById(R.id.pushButton);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                WebSocketSingleton.setWebSocket(webSocket);
                Intent intent = new Intent(getApplicationContext(), OrientationActivity.class);
                startActivity(intent);
            }
        });
    }

}
