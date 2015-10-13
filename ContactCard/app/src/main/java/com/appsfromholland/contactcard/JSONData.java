package com.appsfromholland.contactcard;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;

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

/**
 * Created by Vincent on 6-10-2015.
 */
public class JSONData extends AsyncTask<String, Void, String> {

    // Call back
    private OnRandomUserAvailable listener = null;

    // Static's
    private static final String TAG = "RandomUserTask";
    private static final String urlString = "https://randomuser.me/api/";
    public static Person p;

    public ImageLoader imageLoader;

    // Constructor, set listener
    public JSONData(OnRandomUserAvailable listener, ImageLoader imageLoader) {
        this.listener = listener;
        this.imageLoader = imageLoader;
    }

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

        return response;
    }


    protected void onProgressUpdate(Integer... progress) {
        Log.i(TAG, progress.toString());
    }

    protected void onPostExecute(String response) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // parse JSON and inform caller
        JSONObject jsonObject;

        try {
            // Top level json object
            jsonObject = new JSONObject(response);

            // Get all users and start looping
            JSONArray users = jsonObject.getJSONArray("results");
            for(int idx = 0; idx < users.length(); idx++) {
                // array level objects and get user
                JSONObject array = users.getJSONObject(idx);
                JSONObject userObj = array.getJSONObject("user");
                JSONObject nameObj = userObj.getJSONObject("name");
                JSONObject locationObj = userObj.getJSONObject("location");

                JSONObject picture = userObj.getJSONObject("picture");

                String imageUrl = picture.getString("thumbnail");
                Bitmap image = imageLoader.loadImageSync(imageUrl);

                String gender = userObj.getString("gender");
                String title = nameObj.getString("title");
                String firstName = nameObj.getString("first");
                String lastName = nameObj.getString("last");

                String street = locationObj.getString("street");
                String city = locationObj.getString("city");
                String state = locationObj.getString("state");
                String zip = locationObj.getString("zip");

                String email = userObj.getString("email");
                String username = userObj.getString("username");
                String phone = userObj.getString("phone");
                String cell = userObj.getString("cell");

                String nationality = jsonObject.getString("nationality");

                p = new Person();
                p.image = image;
                p.imageUrl = imageUrl;
                p.gender = gender;
                p.title = title;
                p.firstname = firstName;
                p.lastname = lastName;
                p.street = street;
                p.city = city;
                p.state = state;
                p.zipcode = zip;
                p.email = email;
                p.username = username;
                p.phone = phone;
                p.cell = cell;
                p.nationality = nationality;

                // call back with new person data

                listener.onRandomUserAvailable(p);

            }
        } catch( JSONException ex) {
            Log.e(TAG, ex.getLocalizedMessage());
        }
    }


    //
    // convert InputStream to String
    //
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
    public interface OnRandomUserAvailable {
        void onRandomUserAvailable(Person person);
    }

    public Person getPerson() {
        return p;
    }

}
