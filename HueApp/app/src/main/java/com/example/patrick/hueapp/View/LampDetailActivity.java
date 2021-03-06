package com.example.patrick.hueapp.View;

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

import com.example.patrick.hueapp.Control.LightSendTask;
import com.example.patrick.hueapp.Model.HueLamp;
import com.example.patrick.hueapp.R;

public class LampDetailActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private HueLamp lamp;
    private EditText txtName;
    private ToggleButton btnOn;
    private SeekBar barHue;
    private SeekBar barBri;
    private SeekBar barSat;
    private Button butSend;


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

        Log.i("Bri", Integer.toString(lamp.brightness));
    }

    private void sendToLamp(int id, Boolean on, int bri, int hue, int sat)
    {
        lamp.isOn = on;
        lamp.brightness = bri;
        lamp.color = hue;
        lamp.intensity = sat;

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
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        sendToLamp(lamp.id,btnOn.isChecked(),barBri.getProgress(), barHue.getProgress(), barSat.getProgress());
    }
}