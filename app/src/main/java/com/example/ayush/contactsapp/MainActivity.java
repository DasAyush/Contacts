package com.example.ayush.contactsapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ayush.contactsapp.adapters.ViewPagerAdapter;
import com.example.ayush.contactsapp.fragments.ContactListFragment;
import com.example.ayush.contactsapp.fragments.MessageListFragment;
import com.example.ayush.contactsapp.models.Contact;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private ViewPagerAdapter vPAdapter;
    private static final int SELECT_PHONE_NUMBER = 1;
    private Contact contact;
    private String personName, personNumber;
//    private static final String TAG = MainActivity.class.getName();

    private static final String TAG = "jkl";

    @BindView(R.id.viewPager)
    ViewPager vPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton newContactFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        contact = new Contact();
        Intent intent = getIntent();

        vPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(vPager);
        vPAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        vPAdapter.addFrag(new ContactListFragment(), "Contacts");
        vPAdapter.addFrag(new MessageListFragment(), "Messages");
        vPager.setAdapter(vPAdapter);
        vPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        if (intent!=null)
            Objects.requireNonNull(vPager.getAdapter()).notifyDataSetChanged();

        vPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                final float normalizedPosition = Math.abs(Math.abs(position) - 1);
                page.setScaleX(normalizedPosition / 2 + 0.5f);
                page.setScaleY(normalizedPosition / 2 + 0.5f);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vPager.setCurrentItem(tab.getPosition());
           }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        newContactFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickIntent = new Intent(Intent.ACTION_PICK);
                pickIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(pickIntent, SELECT_PHONE_NUMBER);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECT_PHONE_NUMBER){
            Uri contactsUri = data.getData();
            String projection[] = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};

            assert contactsUri != null;
            Cursor cursor = getApplicationContext().getContentResolver().query(contactsUri,
                    projection, null, null, null);
            if (cursor!=null && cursor.moveToFirst()) {
                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds
                        .Phone.NUMBER);
                int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds
                        .Phone.DISPLAY_NAME_PRIMARY);

                if (nameIndex >= 0) {
                    personName = cursor.getString(nameIndex);
                    contact.setFirst(personName);
                }
                else{
                    contact.setFirst("Anonymous");
                }

                if (numberIndex >= 0){
                    personNumber = cursor.getString(numberIndex);
                    String number = personNumber.replace(" ","").trim();
                    if (!number.startsWith("+91")) {
                        if (number.startsWith("0"))
                            number = number.substring(1);
                        number = "+91" + number;
                    }
                    contact.setNumber(number);
                }
                contact.setLast("");

                Toast.makeText(getApplicationContext(), "Selected phone number : " +
                    nameIndex + " " + personNumber, Toast.LENGTH_SHORT).show();

                Intent detailsIntent = new Intent(MainActivity.this,
                        ContactDetailsActivity.class);
                detailsIntent.putExtra("cont", contact);
                startActivity(detailsIntent);
            }
            assert cursor != null;
            cursor.close();
        }
    }
}
