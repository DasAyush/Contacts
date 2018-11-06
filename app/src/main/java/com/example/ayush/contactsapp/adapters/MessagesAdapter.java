package com.example.ayush.contactsapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ayush.contactsapp.R;
import com.example.ayush.contactsapp.models.Message;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private List<Message> messages;
    private Context context;

    public MessagesAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessagesAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_tab, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesAdapter.ViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.firstName.setText(message.getFirst());
        holder.lastName.setText(message.getLast());

        holder.otp.setText(message.getOtp());
        holder.otp.setVisibility(View.VISIBLE);

        holder.time.setText(message.getTime());
        holder.time.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void setData(List<Message> messages){
        this.messages = messages;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView firstName, lastName, otp, time;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.firstName);
            lastName = itemView.findViewById(R.id.lastName);
            otp = itemView.findViewById(R.id.msgOtp);
            time = itemView.findViewById(R.id.msgTime);
            cardView = itemView.findViewById(R.id.contactCard);
        }

    }
}

