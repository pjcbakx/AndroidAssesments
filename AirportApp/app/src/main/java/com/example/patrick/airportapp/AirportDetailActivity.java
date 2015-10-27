package com.example.patrick.airportapp;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class AirportDetailActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Airport airport;

    private TextView txtName;
    private TextView txtIcao;
    private TextView txtElevation;
    private TextView txtCountry;
    private TextView txtMunicipality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_detail);
        setUpMapIfNeeded();

        txtName = (TextView) findViewById(R.id.txtName);
        txtIcao = (TextView) findViewById(R.id.txtICAO);
        txtElevation = (TextView) findViewById(R.id.txtElevation);
        txtCountry = (TextView) findViewById(R.id.txtCountry);
        txtMunicipality = (TextView) findViewById(R.id.txtMunicipality);


        Bundle extras = getIntent().getExtras();
        airport = (Airport)extras.getSerializable("Airport");

        txtName.setText(airport.name);
        txtIcao.setText(airport.icao);
        txtElevation.setText(Integer.toString(airport.elevation));
        txtCountry.setText(airport.iso_country);
        txtMunicipality.setText(airport.municipality);

        if(mMap != null)
        {
            mMap.clear();


            //Amsterdam cursor
            String countryAmsterdam = "NL";
            LatLng positionAmsterdam = new LatLng(52.3086013794, 4.76388978958);
            mMap.addMarker(new MarkerOptions().position(positionAmsterdam).title("Amsterdam"));


            LatLng position = new LatLng(airport.latitude, airport.longitude);
            Log.i("Detail", "Lat:" + airport.latitude + " , Long:" + airport.longitude);
            mMap.addMarker(new MarkerOptions().position(position).title(airport.name));

            if(airport.iso_country.equals(countryAmsterdam))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 6.0f));
            else
                mMap.moveCamera(CameraUpdateFactory.newLatLng(position));

            PolylineOptions route = new PolylineOptions();
            route.add(positionAmsterdam);
            route.add(position);

            mMap.addPolyline(route);
        }
   }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
}
