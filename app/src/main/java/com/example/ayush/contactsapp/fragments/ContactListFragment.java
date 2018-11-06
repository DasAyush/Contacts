package com.example.ayush.contactsapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ayush.contactsapp.adapters.ContactsAdapter;
import com.example.ayush.contactsapp.R;
import com.example.ayush.contactsapp.models.Contact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ContactListFragment extends Fragment {

    private List<Contact> contactList = new ArrayList<>();
    private Contact contact = new Contact();
    private ContactsAdapter contactsAdapter;
    private RecyclerView contactsRecyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.tab, container, false);
        contactsRecyclerView = v.findViewById(R.id.contactMsgRv);
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArray = jsonObject.getJSONArray("contactList");
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                contact.setFirst(object.getString("first"));
                contact.setLast(object.getString("last"));
                contact.setNumber(object.getString("number"));
                contactList.add(contact);
                contact = new Contact();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        contactsRecyclerView.setLayoutManager(linearLayoutManager);
        contactsAdapter = new ContactsAdapter(getContext(), contactList);
        contactsRecyclerView.setAdapter(contactsAdapter);
        return v;
    }

    /**
     *  method to load the json 'contacts' file from the assets folder
     */
    public String loadJSONFromAsset(){
        String jsonString;
        try {
            InputStream inputStream = getContext().getAssets()
                    .open("contacts.json");
            int size = inputStream.available();
            byte buffer[] = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonString = new String(buffer, "UTF-8");
        }
        catch (IOException io){
            io.printStackTrace();
            return null;
        }
        return jsonString;
    }

}
