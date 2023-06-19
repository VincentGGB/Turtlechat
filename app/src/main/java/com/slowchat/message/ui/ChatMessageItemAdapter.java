/*
        TurtleChat
        Copyright (C) 2023  TurtleChat Open Source Community

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.slowchat.message.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.slowchat.R;
import com.slowchat.contact.domain.ContactModel;
import com.slowchat.contact.domain.ContactViewModel;
import com.slowchat.message.domain.models.MessageModel;

import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

public class ChatMessageItemAdapter extends RecyclerView.Adapter<ChatMessageItemAdapter.ViewHolder> {
    private final List<MessageModel> messages;
    private final String idMachine;
    private Context context;

    public ChatMessageItemAdapter(List<MessageModel> messages, String idMachine) {
        this.messages = messages;
        this.idMachine = idMachine;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.message_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageModel message = messages.get(position);

        boolean isMyMessage = message.getSender().equals(idMachine);
        String idContact = isMyMessage ? message.getReceiver() : message.getSender();

        ContactViewModel contactViewModel = new ContactViewModel();
        ContactModel contact = contactViewModel.getContact(idContact);
        if(contact != null){
            holder.contact.setText(contact.getUsername());
        } else {
            holder.contact.setText(idContact);
        }

        String msg = isMyMessage ? context.getResources().getString(R.string.message_you) + " " + message.getMessage() : message.getMessage();
        holder.message.setText(msg);

        DateFormat f = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        String date;
        if(message.getReceptionDate() != null) {
            date = f.format(message.getReceptionDate());
        } else {
            date = "";
        }
        holder.date.setText(date);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView contact;
        TextView message;
        TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            contact = itemView.findViewById(R.id.message_item_contact);
            message = itemView.findViewById(R.id.message_item_message);
            date = itemView.findViewById(R.id.message_item_date);

            itemView.setOnClickListener(l -> {
                Intent chatActivityIntent = new Intent(context, ChatActivity.class);
                int position = getAdapterPosition();
                MessageModel message = messages.get(position);
                chatActivityIntent.putExtra("contact", message.getReceiver().equals(idMachine) ? message.getSender() : message.getReceiver());
                context.startActivity(chatActivityIntent);
            });

        }
    }
}
