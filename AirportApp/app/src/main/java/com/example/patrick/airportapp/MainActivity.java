package com.example.patrick.airportapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private final static String TAG = "MainActivity";
    private AirportAdapter airportCursorAdapter;
    private ListView airportListView;

    ArrayList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<String>();

        // Inflate listview
        airportListView = (ListView) findViewById(R.id.airPortListView);
        airportListView.setOnItemClickListener(this);

        AirportsDatabase adb = new AirportsDatabase(this);
        Cursor cursor = adb.getAirports();

        cursor.moveToFirst();
        while( cursor.moveToNext() ) {
            String str = cursor.getString(cursor.getColumnIndex("name"));
            list.add(str);
            Log.i(TAG, str);
        }
        Log.i(TAG, "count: " + list.size());

        AirportAdapter adapter = new AirportAdapter(getApplicationContext(), getLayoutInflater(),list);
        airportListView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(getApplicationContext(),AirportDetailActivity.class);
        i.putExtra("Airport", (Serializable) list.get(position));

        startActivity(i);
    }
}
