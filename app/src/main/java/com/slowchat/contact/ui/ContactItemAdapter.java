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
package com.slowchat.contact.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.slowchat.R;
import com.slowchat.contact.domain.ContactModel;
import com.slowchat.contact.domain.ContactViewModel;
import com.slowchat.message.ui.ChatActivity;

import java.util.List;

public class ContactItemAdapter extends RecyclerView.Adapter<ContactItemAdapter.ViewHolder> {
    private final ContactViewModel viewModel;
    private final List<ContactModel> contacts;

    private Context context;

    public ContactItemAdapter(ContactViewModel viewModel) {
        this.viewModel = viewModel;
        this.contacts = viewModel.getContacts();
    }

    public void insertContact(ContactModel contact) {
        int position = viewModel.getPosition(contact);
        this.contacts.add(position, contact);
        notifyItemInserted(position);
    }

    public void updateContact(ContactModel contact, int position) {
        int newPosition = viewModel.getPosition(contact);
        this.contacts.remove(position);
        this.contacts.add(newPosition, contact);
        notifyItemMoved(position, newPosition);
        notifyItemChanged(newPosition);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContactModel contact = contacts.get(position);
        String username = contact.getUsername();

        holder.contact.setText(username);
        holder.letter.setText(String.valueOf(username.charAt(0)).toUpperCase());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView contact;
        TextView letter;
        Button edit;
        Button delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contact = itemView.findViewById(R.id.contact_item_contact);
            letter = itemView.findViewById(R.id.contact_item_letter);
            edit = itemView.findViewById(R.id.contact_item_edit);
            delete = itemView.findViewById(R.id.contact_item_delete);

            itemView.setOnClickListener(l -> {
                int position = getAdapterPosition();
                ContactModel contact = contacts.get(position);
                Intent chatActivityIntent = new Intent(context, ChatActivity.class);
                chatActivityIntent.putExtra("contact", contact.getIdContact());
                context.startActivity(chatActivityIntent);
            });

            edit.setOnClickListener(l -> {
                int position = getAdapterPosition();
                ContactModel contact = contacts.get(position);

                new DialogContact(itemView.getContext(), contact, c -> {
                    if (viewModel.updateContact(c)) {
                        updateContact(c, position);
                    }
                }).show();
            });

            delete.setOnClickListener(l -> {
                new MaterialAlertDialogBuilder(itemView.getContext())
                        .setTitle(R.string.remove_contact)
                        .setMessage(R.string.remove_contact_description)
                        .setPositiveButton(R.string.remove, (dialogInterface, i) -> {
                            int position = getAdapterPosition();
                            ContactModel contact = contacts.get(position);

                            if (viewModel.removeContact(contact)) {
                                contacts.remove(position);
                                notifyItemRemoved(position);
                            }
                        })
                        .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {})
                        .show();
            });
        }
    }
}
