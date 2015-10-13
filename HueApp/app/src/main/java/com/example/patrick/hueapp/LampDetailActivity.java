package com.example.patrick.hueapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class LampDetailActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private HueLamp lamp;
    private EditText txtName;
    private ToggleButton btnOn;
    private SeekBar barHue;
    private SeekBar barBri;
    private SeekBar barSat;
    private Button butSend;

    private boolean tracking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp_detail);

        Bundle extras = getIntent().getExtras();
        lamp = (HueLamp)extras.getSerializable("Lamp");

        txtName = (EditText) findViewById(R.id.editTextName);
        btnOn = (ToggleButton) findViewById(R.id.toggleButtonOn);
        barHue = (SeekBar) findViewById(R.id.seekBarHue);
        barBri = (SeekBar) findViewById(R.id.seekBarBri);
        barSat = (SeekBar) findViewById(R.id.seekBarSat);
        butSend = (Button) findViewById(R.id.buttonSend);
        butSend.setOnClickListener(this);

        btnOn.setOnClickListener(this);
        barHue.setOnSeekBarChangeListener(this);
        barBri.setOnSeekBarChangeListener(this);
        barSat.setOnSeekBarChangeListener(this);

        txtName.setText(lamp.name);
        btnOn.setChecked(lamp.isOn);
        barHue.setProgress(lamp.color);
        barBri.setProgress(lamp.brightness);
        barSat.setProgress(lamp.intensity);
    }

    private void sendToLamp(int id, Boolean on, int bri, int hue, int sat)
    {
        LightSendTask task = new LightSendTask();
        task.execute(Integer.toString(id),on.toString(), Integer.toString(bri), Integer.toString(hue),Integer.toString(sat));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lamp_detail, menu);
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
    public void onClick(View v) {
        sendToLamp(lamp.id,btnOn.isChecked(),barBri.getProgress(), barHue.getProgress(), barSat.getProgress());
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(!tracking)
            sendToLamp(lamp.id,btnOn.isChecked(),barBri.getProgress(), barHue.getProgress(), barSat.getProgress());
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        tracking = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        tracking = false;
        sendToLamp(lamp.id,btnOn.isChecked(),barBri.getProgress(), barHue.getProgress(), barSat.getProgress());
    }
}