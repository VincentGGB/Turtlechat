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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.slowchat.R;
import com.slowchat.contact.domain.ContactViewModel;
import com.slowchat.utilities.InterfaceErrorMessages;
import com.slowchat.utilities.error.ToastError;

public class FragmentContacts extends Fragment {
    ContactViewModel viewModel;
    ContactItemAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        viewModel = new ContactViewModel();
        adapter = new ContactItemAdapter(viewModel);

        RecyclerView recyclerView = view.findViewById(R.id.fragment_contacts_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = view.findViewById(R.id.fragment_contacts_fab);
        fab.setOnClickListener(l -> new DialogContact(view.getContext(), null, c -> {
            if (viewModel.addContact(c)) {
                adapter.insertContact(c);
            }else{
                new ToastError(view.getContext()).createToast(InterfaceErrorMessages.EventType.CONTACT_CREATION_FAILED);
            }
        }).show());
    }
}
