package com.appsfromholland.contactcard.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.database.Cursor;

import com.appsfromholland.contactcard.Control.ContactCardDatabase;
import com.appsfromholland.contactcard.Control.JSONData;
import com.appsfromholland.contactcard.Model.Person;
import com.appsfromholland.contactcard.Control.PersonAdapter;
import com.appsfromholland.contactcard.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.lang.String;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, JSONData.OnRandomUserAvailable {

    ListView mPersonListView;
    PersonAdapter mPersonAdapter;
    ArrayList mPersonList = new ArrayList();
    ContactCardDatabase ccdb;
    JSONData jsonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));

        for (int i = 0; i < 10; i++) {
            jsonData = new JSONData(this, imageLoader);
            String[] urls = new String[]{"https://randomuser.me/api/"};
            jsonData.execute(urls);
        }

        ccdb = new ContactCardDatabase(this);
        Cursor cursor = ccdb.getCards();

        cursor.moveToFirst();
        while( cursor.moveToNext() ) {
            Person p = new Person();

            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String imageUrl = cursor.getString(cursor.getColumnIndex("image"));
            Bitmap image = imageLoader.loadImageSync(imageUrl);

            String gender = cursor.getString(cursor.getColumnIndex("gender"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String firstname = cursor.getString(cursor.getColumnIndex("firstname"));
            String lastname = cursor.getString(cursor.getColumnIndex("lastname"));
            String street = cursor.getString(cursor.getColumnIndex("street"));
            String city = cursor.getString(cursor.getColumnIndex("city"));
            String state = cursor.getString(cursor.getColumnIndex("state"));
            String zipcode = cursor.getString(cursor.getColumnIndex("zipcode"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String cell = cursor.getString(cursor.getColumnIndex("cell"));
            String nationality = cursor.getString(cursor.getColumnIndex("nationality"));

            p.id = id;
            p.image = image;
            p.imageUrl =  imageUrl.toString();
            p.gender = gender.toString();
            p.title = title.toString();
            p.firstname = firstname.toString();
            p.lastname = lastname.toString();
            p.street = street.toString();
            p.city = city.toString();
            p.state = state.toString();
            p.zipcode = zipcode.toString();
            p.email = email.toString();
            p.username = username.toString();
            p.phone = phone.toString();
            p.cell = cell.toString();
            p.nationality = nationality.toString();

            mPersonList.add(p);

        }

        mPersonListView = (ListView) findViewById(R.id.personListView);
        mPersonAdapter = new PersonAdapter(this, getLayoutInflater(), mPersonList);
        mPersonListView.setAdapter(mPersonAdapter);
        mPersonAdapter.notifyDataSetChanged();
        mPersonListView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent i = new Intent(getApplicationContext(), PersonActivity.class);
        Person person = (Person) mPersonList.get(position);

        i.putExtra("IMAGE", person.image);
        i.putExtra("GENDER", person.gender);
        i.putExtra("TITLE", person.title);
        i.putExtra("FIRSTNAME", person.firstname);
        i.putExtra("LASTNAME", person.lastname);
        i.putExtra("STREET", person.street);
        i.putExtra("CITY", person.city);
        i.putExtra("STATE", person.state);
        i.putExtra("ZIPCODE", person.zipcode);
        i.putExtra("EMAIL", person.email);
        i.putExtra("USERNAME", person.username);
        i.putExtra("PHONE", person.phone);
        i.putExtra("CELL", person.cell);
        i.putExtra("NATIONALITY", person.nationality);
        startActivity(i);
    }

    @Override
    public void onRandomUserAvailable(Person person) {
        mPersonList.add(person);
        //ccdb.addCard(person); // Voor het wegschrijven naar database
        mPersonAdapter.notifyDataSetChanged();
    }

}
