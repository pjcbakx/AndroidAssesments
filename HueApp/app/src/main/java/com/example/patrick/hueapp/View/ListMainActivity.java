package com.example.patrick.hueapp.View;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.patrick.hueapp.Control.LampAdapter;
import com.example.patrick.hueapp.Control.LightReadTask;
import com.example.patrick.hueapp.Control.LightSendTask;
import com.example.patrick.hueapp.Model.HueLamp;
import com.example.patrick.hueapp.R;

import java.io.Serializable;
import java.util.ArrayList;

public class ListMainActivity extends AppCompatActivity implements LightReadTask.LightAvailable, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ListView lightsListView = null;
    private TextView textViewConnect = null;

    private Button buttonAllOn = null;
    private Button buttonAllOff = null;
    private Button buttonDisco = null;

    private ArrayList<HueLamp> lights = new ArrayList<HueLamp>();
    private LampAdapter lampAdapter = null;

    private static final String TAG = "Main";
    private Button addOneLightBtn = null;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_main);

        //Finding layout objects
        textViewConnect = (TextView) findViewById(R.id.textViewConnect);
        buttonAllOn = (Button) findViewById(R.id.buttonAllOn);
        buttonAllOff = (Button) findViewById(R.id.buttonAllOff);

        lightsListView = (ListView) findViewById(R.id.lightsListView);
        lampAdapter = new LampAdapter(getApplicationContext(),
                getLayoutInflater(), lights);
        lightsListView.setAdapter(lampAdapter);

        lightsListView.setOnItemClickListener(this);

        buttonAllOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (HueLamp lamp : lights) {
                    lamp.isOn = true;
                    lamp.brightness = 255;
                    LightSendTask sendTask = new LightSendTask();
                    sendTask.execute(Integer.toString(lamp.id), "true", Integer.toString(255));
                }
                lampAdapter.notifyDataSetChanged();
            }
        });

        buttonAllOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (HueLamp lamp : lights) {
                    lamp.isOn = false;
                    lamp.brightness = 0;
                    LightSendTask sendTask = new LightSendTask();
                    sendTask.execute(Integer.toString(lamp.id), "false", Integer.toString(0));
                }
                lampAdapter.notifyDataSetChanged();
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        textViewConnect.setText("Connecting to bridge....");
        buttonAllOn.setVisibility(View.GONE);
        buttonAllOff.setVisibility(View.GONE);

        LightReadTask getLights = new LightReadTask(this);
        getLights.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLampAvailable(HueLamp lamp, boolean connected) {
        if(connected) {
            lights.add(lamp);
            lampAdapter.notifyDataSetChanged();

            textViewConnect.setVisibility(View.GONE);
            buttonAllOn.setVisibility(View.VISIBLE);
            buttonAllOff.setVisibility(View.VISIBLE);
        }
        else
        {
            textViewConnect.setVisibility(View.VISIBLE);
            textViewConnect.setText("Connection to bridge failed");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(getApplicationContext(), LampDetailActivity.class);
        i.putExtra("Lamp", (Serializable) lights.get(position));

        startActivity(i);
    }

    @Override
    public void onRefresh() {
        lights.clear();
        buttonAllOn.setVisibility(View.GONE);
        buttonAllOff.setVisibility(View.GONE);
        textViewConnect.setVisibility(View.VISIBLE);
        textViewConnect.setText("Connecting to bridge....");

        LightReadTask getLights = new LightReadTask(this);
        getLights.execute();
        lampAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}
