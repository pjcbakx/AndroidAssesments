package com.example.patrick.hueapp;

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

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

public class ListMainActivity extends AppCompatActivity implements LightReadTask.LightAvailable, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ListView lightsListView = null;
    private TextView textViewConnect = null;

    private ArrayList<HueLamp> lights = new ArrayList<HueLamp>();
    private LampAdapter lampAdapter = null;

    private static final String TAG = "Main";
    private Button addOneLightBtn = null;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_main);

        textViewConnect = (TextView) findViewById(R.id.textViewConnect);
        lightsListView = (ListView) findViewById(R.id.lightsListView);
        lampAdapter = new LampAdapter(getApplicationContext(),
                getLayoutInflater(), lights);
        lightsListView.setAdapter(lampAdapter);

        lightsListView.setOnItemClickListener(this);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        textViewConnect.setText("Connecting to bridge....");
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

            textViewConnect.setVisibility(View.INVISIBLE);
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
        textViewConnect.setVisibility(View.VISIBLE);
        textViewConnect.setText("Connecting to bridge....");
        LightReadTask getLights = new LightReadTask(this);
        getLights.execute();
        lampAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}
