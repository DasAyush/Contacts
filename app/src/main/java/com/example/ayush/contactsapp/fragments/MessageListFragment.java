package com.example.ayush.contactsapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ayush.contactsapp.MainActivity;
import com.example.ayush.contactsapp.R;
import com.example.ayush.contactsapp.adapters.MessagesAdapter;
import com.example.ayush.contactsapp.models.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageListFragment extends Fragment {

    private Message message = new Message();
    private List<Message> messages = new ArrayList<>();
    private MessagesAdapter messagesAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView msgRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tab, container, false);
        msgRecyclerView = v.findViewById(R.id.contactMsgRv);
        messages.add(new Message("Ayush","Das","Your OTP is : 123456",
                "12:35 pm" ));
        messagesAdapter = new MessagesAdapter(getContext(), messages);
        linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        msgRecyclerView.setLayoutManager(linearLayoutManager);
        msgRecyclerView.setAdapter(messagesAdapter);


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments()!=null) {
            message = getArguments().getParcelable("msg");
            messages.add(message);
            messagesAdapter.setData(messages);

            Intent intent = new Intent(getContext(), MainActivity.class);
            intent.putExtra("update", "updateViewPager");
            startActivity(intent);
        }
        messagesAdapter.notifyDataSetChanged();
    }
}
