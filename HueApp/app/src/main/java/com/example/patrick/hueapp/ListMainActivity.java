package com.example.patrick.hueapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ListMainActivity extends AppCompatActivity implements LightTask.LightAvailable, AdapterView.OnItemClickListener {

    private ListView lightsListView = null;

    private ArrayList<HueLamp> lights = new ArrayList<HueLamp>();
    private LampAdapter lampAdapter = null;

    private static final String TAG = "Main";
    private Button addOneLightBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_main);

        lightsListView = (ListView) findViewById(R.id.lightsListView);
        lampAdapter = new LampAdapter(getApplicationContext(),
                getLayoutInflater(), lights);
        lightsListView.setAdapter(lampAdapter);

        lightsListView.setOnItemClickListener(this);

        LightTask getRandomUser = new LightTask(this);
        String[] urls = new String[] { "http://192.168.1.179/api/23d82f45b1c1476a645111275ea73/lights/1" };
        getRandomUser.execute(urls);
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
    public void onLampAvailable(HueLamp lamp) {
        lights.add(lamp);
        lampAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(getApplicationContext(), LampDetailActivity.class);
        i.putExtra("Lamp", (Serializable)lights.get(position));

        startActivity(i);
    }
}
