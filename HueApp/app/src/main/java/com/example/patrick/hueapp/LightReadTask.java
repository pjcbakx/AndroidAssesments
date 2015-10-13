package com.example.patrick.hueapp;

import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewDebug;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class LightReadTask extends AsyncTask<String, Void, String>  {

    // Call back
    private LightAvailable listener = null;

    // Static's
    private static final String TAG = "RandomUserTask";
    private static final String urlString = "http://192.168.1.179/api/23d82f45b1c1476a645111275ea73";

    // Constructor, set listener
    public LightReadTask(LightAvailable listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {

        InputStream inputStream = null;
        int responsCode = -1;

        String response = "";

        for(String url : params) {
            Log.i(TAG, url);
        }

        try {
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();

            if (!(urlConnection instanceof HttpURLConnection)) {
                // Url
                return null;
            }

            HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
            httpConnection.setAllowUserInteraction(false);
            httpConnection.setInstanceFollowRedirects(true);
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            Log.i(TAG, "htpp connect");
            responsCode = httpConnection.getResponseCode();

            if (responsCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpConnection.getInputStream();
                response = getStringFromInputStream(inputStream);
                //Log.i(TAG, response);
            }
        } catch (MalformedURLException e) {
            Log.e("TAG", e.getLocalizedMessage());
            return null;
        } catch (IOException e) {
            Log.e("TAG", e.getLocalizedMessage());
            return null;
        }
        Log.i(TAG, response);
        return response;
    }


    protected void onProgressUpdate(Integer... progress) {
        Log.i(TAG, progress.toString());
    }

    protected void onPostExecute(String response) {

        JSONObject jsonObject;

        try {
            // Top level json object
            jsonObject = new JSONObject(response);

            JSONObject lamps = jsonObject.getJSONObject("lights");
            Log.i("Lights", lamps.toString());

            int lengtIndex = 1;
            int readIndex = 1;
            while(lengtIndex <= lamps.length())
            {
                if(!lamps.isNull(readIndex + ""))
                {
                    JSONObject light = lamps.getJSONObject(readIndex + "");
                    String name = light.getString("name");
                    JSONObject state = light.getJSONObject("state");
                    boolean isOn = state.getBoolean("on");
                    int bri = state.getInt("bri");

                    int hue = -1;
                    int sat = -1;

                    if (!state.isNull("hue"))
                        hue = state.getInt("hue");
                    if (!state.isNull("sat"))
                        sat = state.getInt("sat");

                    HueLamp l = new HueLamp();
                    l.id = readIndex;
                    l.name = name;
                    l.isOn = isOn;
                    l.brightness = bri;
                    l.color = hue;
                    l.intensity = sat;

                    listener.onLampAvailable(l);
                    lengtIndex++;
                }
                readIndex++;
            }
        } catch( JSONException ex) {
            Log.e(TAG, ex.getLocalizedMessage());
        }
    }


    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    // Call back interface
    public interface LightAvailable {
        void onLampAvailable(HueLamp person);
    }
}