package com.example.patrick.airportapp.View;

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
import android.widget.Spinner;

import com.example.patrick.airportapp.Control.AirportAdapter;
import com.example.patrick.airportapp.Control.AirportsDatabase;
import com.example.patrick.airportapp.Model.Airport;
import com.example.patrick.airportapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private final static String TAG = "MainActivity";
    private AirportAdapter airportCursorAdapter;
    private ListView airportListView;
    private Spinner countrySpinner;
    private AirportsDatabase adb;
    private AirportAdapter adapter;

    ArrayList airportList;
    ArrayList countryList;

    String startCountry = "NL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        airportList = new ArrayList<Airport>();
        countryList = new ArrayList<String>();

        countrySpinner = (Spinner) findViewById(R.id.spinner);

        // Inflate listview
        airportListView = (ListView) findViewById(R.id.airPortListView);
        airportListView.setOnItemClickListener(this);

        adb = new AirportsDatabase(this);

        Cursor cursor = adb.getCountries();

        cursor.moveToFirst();
        while( cursor.moveToNext() ) {

            String str = cursor.getString(cursor.getColumnIndex("iso_country"));

            if(!countryList.contains(str))
                countryList.add(str);

            //Log.i(TAG, str);
        }
        Collections.sort(countryList, String.CASE_INSENSITIVE_ORDER);

        ArrayAdapter<String> stringArrayAdapter=
                new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        countryList);
        countrySpinner.setAdapter(stringArrayAdapter);
        countrySpinner.setSelection(countryList.indexOf(startCountry), false);
        countrySpinner.setOnItemSelectedListener(this);

        adapter = new AirportAdapter(getApplicationContext(), getLayoutInflater(),airportList);
        airportListView.setAdapter(adapter);
        fillAirportList(startCountry);

        Collections.sort(airportList, new Comparator<Airport>() {
            @Override
            public int compare(Airport lhs, Airport rhs) {
                return lhs.name.compareTo(rhs.name);
            }
        });

        adapter.notifyDataSetChanged();
    }

    private void fillCountryList()
    {
        Cursor cursor = adb.getCountries();

        cursor.moveToFirst();
        while( cursor.moveToNext() ) {

            String str = cursor.getString(cursor.getColumnIndex("iso_country"));

            if(!countryList.contains(str))
                countryList.add(str);

            //Log.i(TAG, str);
        }
    }

    private void fillAirportList(String country)
    {
        Cursor cursor = adb.getAirports(country);

        cursor.moveToFirst();
        Airport airport = new Airport();

        //Get info of the first element in the cursor, this item is not read in the while loop
        airport.name = cursor.getString(cursor.getColumnIndex("name"));;
        airport.icao = cursor.getString(cursor.getColumnIndex("icao"));
        airport.longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
        airport.latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
        airport.elevation = cursor.getInt(cursor.getColumnIndex("elevation"));
        airport.iso_country = cursor.getString(cursor.getColumnIndex("iso_country"));
        airport.municipality = cursor.getString(cursor.getColumnIndex("municipality"));

        airportList.add(airport);

        while( cursor.moveToNext()) {

            airport = new Airport();

            String name = (String) cursor.getString(cursor.getColumnIndex("name"));
            airport.name = name;
            airport.icao = cursor.getString(cursor.getColumnIndex("icao"));
            airport.longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
            airport.latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
            airport.elevation = cursor.getInt(cursor.getColumnIndex("elevation"));
            airport.iso_country = cursor.getString(cursor.getColumnIndex("iso_country"));
            airport.municipality = cursor.getString(cursor.getColumnIndex("municipality"));

            airportList.add(airport);
            //Log.i(TAG, str);
        }
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
        i.putExtra("Airport", (Serializable) airportList.get(position));

        startActivity(i);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String name = (String) countryList.get(position);
        Log.i("Test", name);
        airportList.clear();
        fillAirportList(name);

        Collections.sort(airportList, new Comparator<Airport>() {
            @Override
            public int compare(Airport lhs, Airport rhs) {
                return lhs.name.compareTo(rhs.name);
            }
        });

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
