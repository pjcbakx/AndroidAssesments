package com.appsfromholland.contactcard;

import android.graphics.Bitmap;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Vincent on 3-10-2015.
 */
public class PersonActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.contact_card);

        Bundle extras = getIntent().getExtras();
        Bitmap image = extras.getParcelable("IMAGE");
        String gender = extras.getString("GENDER");
        String title = extras.getString("TITLE");
        String firstname = extras.getString("FIRSTNAME");
        String lastname = extras.getString("LASTNAME");
        String street = extras.getString("STREET");
        String city = extras.getString("CITY");
        String state = extras.getString("STATE");
        String zipcode = extras.getString("ZIPCODE");
        String email = extras.getString("EMAIL");
        String username = extras.getString("USERNAME");
        String phone = extras.getString("PHONE");
        String cell = extras.getString("CELL");
        String nationality = extras.getString("NATIONALITY");

        ImageView ivImage = (ImageView) findViewById(R.id.profile_image);
        TextView tvGender = (TextView) findViewById(R.id.gender);
        TextView tvTitle = (TextView) findViewById(R.id.title);
        TextView tvFirstname = (TextView) findViewById(R.id.firstname);
        TextView tvLastname = (TextView) findViewById(R.id.lastname);
        TextView tvStreet = (TextView) findViewById(R.id.street);
        TextView tvCity = (TextView) findViewById(R.id.city);
        TextView tvState = (TextView) findViewById(R.id.state);
        TextView tvZipcode = (TextView) findViewById(R.id.zipcode);
        TextView tvEmail = (TextView) findViewById(R.id.email);
        TextView tvUsername = (TextView) findViewById(R.id.username);
        TextView tvPhone = (TextView) findViewById(R.id.phone);
        TextView tvCell = (TextView) findViewById(R.id.cell);
        TextView tvNationality = (TextView) findViewById(R.id.nationality);

        ivImage.setImageBitmap(image);
        tvGender.setText(gender);
        tvTitle.setText(title);
        tvFirstname.setText(firstname);
        tvLastname.setText(lastname);
        tvStreet.setText(street);
        tvCity.setText(city);
        tvState.setText(state);
        tvZipcode.setText(zipcode);
        tvEmail.setText(email);
        tvUsername.setText(username);
        tvPhone.setText(phone);
        tvCell.setText(cell);
        tvNationality.setText(nationality);

    }

}