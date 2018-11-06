package com.example.ayush.contactsapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ayush.contactsapp.ContactDetailsActivity;
import com.example.ayush.contactsapp.R;
import com.example.ayush.contactsapp.models.Contact;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private List<Contact> contactList;
    private Context context;

    public ContactsAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactsAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_tab, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.firstName.setText(contact.getFirst());
        holder.lastName.setText(contact.getLast());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView firstName, lastName;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.firstName);
            lastName = itemView.findViewById(R.id.lastName);
            cardView = itemView.findViewById(R.id.contactCard);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.contactCard :
                    Contact contact = contactList.get(getAdapterPosition());
                    Intent intent = new Intent(context, ContactDetailsActivity.class);
                    intent.putExtra("cont", contact);
                    context.startActivity(intent);
            }
        }
    }
}
