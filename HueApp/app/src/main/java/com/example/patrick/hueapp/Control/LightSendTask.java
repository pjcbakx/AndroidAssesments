package com.example.patrick.hueapp.Control;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by patrick on 9-10-2015.
 */
public class LightSendTask extends AsyncTask<String,Void,String> {
    private static final String TAG = "LightSendTask";
    private static String baseUrlString = "";
    private static final String userId = "3f42aff116cdeb1770120252548eb4b";
    private static final String hueIp = "http://192.168.1.179/api/";
    private String lampId;

    @Override
    protected String doInBackground(String... params) {
        InputStream inputStream = null;
        int responsCode = -1;
        String urlString = "";
        Boolean on = false;
        int bri = 0;
        int hue = 255;
        int sat = 0;

        String response = "";

        baseUrlString = hueIp + userId + "/lights/";

        for(int i = 0; i < params.length; i++)
        {
            switch(i) {
                case 0:
                    urlString = baseUrlString + params[i] + "/state";

                    break;
                case 1:
                    if(params[i] == "true")
                        on = true;
                    break;
                case 2:
                    Log.i("int", params[i]);
                    bri = Integer.parseInt(params[i]);
                    break;
                case 3:
                    hue = Integer.parseInt(params[i]);
                    break;
                case 4:
                    sat = Integer.parseInt(params[i]);
                    break;
            }
        }
        try {
            URL url = new URL(urlString);
           URLConnection urlConnection = url.openConnection();

           if (!(urlConnection instanceof HttpURLConnection)) {
                // Url
               return null;
           }

           HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
           httpConnection.setAllowUserInteraction(true);
           httpConnection.setInstanceFollowRedirects(true);
           httpConnection.setDoInput(true);
           httpConnection.setDoOutput(true);
           httpConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
           httpConnection.setRequestProperty("Accept", "application/json");
           httpConnection.setRequestMethod("PUT");

           Log.i(TAG, "Connect");
           httpConnection.connect();

            JSONObject param = new JSONObject();
            param.put("on", on);
            param.put("bri",bri);
            param.put("hue",hue);
            param.put("sat", sat);

           OutputStream out = httpConnection.getOutputStream();
           out.write(param.toString().getBytes("UTF-8"));
           out.close();

           StringBuilder sb = new StringBuilder();
           responsCode = httpConnection.getResponseCode();

           if(responsCode == HttpURLConnection.HTTP_OK)
           {
               BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), "utf-8"));
               String line = null;
               while ((line = br.readLine()) != null) {
                   sb.append(line + "\n");
               }
               br.close();

               System.out.println("String Builder " + sb.toString());
           }

        } catch (MalformedURLException e) {
            Log.e("TAG", e.getLocalizedMessage());
            return null;
        } catch (IOException e) {
            Log.e("TAG", e.getLocalizedMessage());
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Success";
    }
}
