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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.slowchat.R;
import com.slowchat.message.domain.models.MessageModel;

import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

public class ChatConversationItemAdapter extends RecyclerView.Adapter<ChatConversationItemAdapter.ViewHolder> {

    private final List<MessageModel> messages;
    private final String idMachine;

    private enum messageType {
        ME,
        OTHER;

        public int getValue() {
            switch (this) {
                case ME:
                    return 1;
                case OTHER:
                default:
                    return 0;
            }
        }
    }

    public void insertContact(MessageModel message) {
        this.messages.add(message);
        notifyItemInserted(messages.size());
    }

    public ChatConversationItemAdapter(List<MessageModel> messages, String idMachine) {
        this.messages = messages;
        this.idMachine = idMachine;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view;
        if (viewType == messageType.OTHER.getValue()) {
            view = LayoutInflater.from(context).inflate(R.layout.conversation_message_item_left, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.conversation_message_item_right, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageModel message = messages.get(position);

        String msg = message.getMessage();
        holder.message.setText(msg);

        DateFormat f = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        String date;
        if (message.getSentDate() != null) {
            date = f.format(message.getSentDate());
        } else {
            date = "";
        }
        holder.date.setText(date);

        if (message.getReceptionDate() != null) {
            holder.view.setVisibility(View.VISIBLE);
        } else {
            holder.view.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        MessageModel message = messages.get(position);
        if (message.getSender().equals(idMachine)) {
            return messageType.ME.getValue();
        } else {
            return messageType.OTHER.getValue();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView message;
        TextView date;
        ImageView view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.message_item_message);
            date = itemView.findViewById(R.id.message_item_date);
            view = itemView.findViewById(R.id.message_item_view);

            itemView.setOnClickListener(l -> {
                int position = getAdapterPosition();
                MessageModel message = messages.get(position);
            });
        }
    }
}
