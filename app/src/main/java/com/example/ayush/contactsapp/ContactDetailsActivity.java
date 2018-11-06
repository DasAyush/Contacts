package com.example.ayush.contactsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ayush.contactsapp.models.Contact;

public class ContactDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Contact contact;
    private TextView first, last, phone;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        first = findViewById(R.id.firstName);
        last = findViewById(R.id.lastName);
        phone = findViewById(R.id.phoneNumber);
        send = findViewById(R.id.sendMessage);

        Intent intent = getIntent();
        if (intent!=null && intent.getExtras()!=null)
            contact = (Contact) intent.getExtras().get("cont");

        first.setText(contact.getFirst());
        last.setText(contact.getLast());
        phone.setText(contact.getNumber());

        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent otpIntent = new Intent(getApplicationContext(), OtpActivity.class);
        otpIntent.putExtra("cont", contact);
        startActivity(otpIntent);
    }
}
