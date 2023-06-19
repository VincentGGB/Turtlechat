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

import static com.slowchat.R.id.menu_add_contact;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.slowchat.R;
import com.slowchat.appdata.domain.AppDataViewModel;
import com.slowchat.contact.domain.ContactModel;
import com.slowchat.contact.domain.ContactViewModel;
import com.slowchat.contact.ui.DialogContact;
import com.slowchat.message.domain.ChatViewModel;
import com.slowchat.message.domain.models.MessageModel;
import com.slowchat.utilities.InterfaceErrorMessages;
import com.slowchat.utilities.error.ToastError;

import java.util.Date;

public class ChatActivity extends AppCompatActivity {
    private static final String idMachine = AppDataViewModel.getAppDataModel().getIdMachine();

    ContactModel contactModel = new ContactModel();
    ChatViewModel chatViewModel = new ChatViewModel();

    ChatConversationItemAdapter adapter;
    RecyclerView recyclerView;

    private boolean contactUnknown;
    private MenuItem itemAddContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ContactViewModel contactViewModel = new ContactViewModel();
        String contact = getIntent().getStringExtra("contact");
        this.contactModel = contactViewModel.getContact(contact);

        if (this.contactModel == null) {
            contactModel = new ContactModel();
            contactModel.setIdContact(contact);
        }

        contactUnknown = contactModel.getUsername() == null;

        setContentView(R.layout.activity_chat);
        recyclerView = findViewById(R.id.chat_recyclerview);

        setToolBar();
        setMessageList();
        setSendMessageForm();
        setupInsets(getWindow());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu, menu);
        itemAddContact = menu.findItem(menu_add_contact);
        itemAddContact.setVisible(contactUnknown);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case menu_add_contact:
                new DialogContact(this, contactModel, c -> {
                    if (new ContactViewModel().addContact(c)) {
                        Toast.makeText(this, "Contact successfully added", Toast.LENGTH_SHORT).show();
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setTitle(c.getUsername());
                            itemAddContact.setVisible(false);
                        }
                    }
                }).show();
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setToolBar() {
        setSupportActionBar(findViewById(R.id.chat_toolbar));
        if (getSupportActionBar() != null) {
            if (contactModel != null && contactModel.getUsername() != null && !contactModel.getUsername().equals("")) {
                getSupportActionBar().setTitle(contactModel.getUsername());
            } else {
                getSupportActionBar().setTitle(contactModel.getIdContact());
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setMessageList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ChatConversationItemAdapter(chatViewModel.getMessagesOfConversation(contactModel.getIdContact()), idMachine);
        recyclerView.setAdapter(adapter);
    }

    private void setSendMessageForm() {
        Button sendButton = findViewById(R.id.chat_message_send);
        EditText editText = findViewById(R.id.chat_message_text);

        sendButton.setOnClickListener(l -> {
            String message = editText.getText().toString();
            MessageModel messageModel = new MessageModel(message, idMachine, contactModel.getIdContact(), new Date(), null);

            if (!chatViewModel.sendMessage(messageModel)){
                new ToastError(this).createToast(InterfaceErrorMessages.EventType.MESSAGE_SEND_FAILED);
            }
            editText.setText(null);

            adapter.insertContact(messageModel);
            recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable e) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sendButton.setEnabled(!editText.getText().toString().trim().isEmpty());
            }
        });
    }

    private void setupInsets(Window window) {
        WindowCompat.setDecorFitsSystemWindows(window, false);

        View appbarLayout = findViewById(R.id.chat_appbar_layout);
        View sendLayout = findViewById(R.id.chat_send_layout);

        ViewCompat.setOnApplyWindowInsetsListener(appbarLayout, (v, insets) -> {
            Insets i = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, i.top, 0, 0);

            return insets;
        });

        ViewCompat.setOnApplyWindowInsetsListener(sendLayout, (v, insets) -> {
            Insets i = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(16, 16, 16, 16 + i.bottom);

            return insets;
        });

        ViewCompat.setWindowInsetsAnimationCallback(sendLayout,
                new TranslateDeferringInsetsAnimationCallback(sendLayout));

        ViewCompat.setWindowInsetsAnimationCallback(recyclerView,
                new TranslateDeferringInsetsAnimationCallback(recyclerView));
    }
}
