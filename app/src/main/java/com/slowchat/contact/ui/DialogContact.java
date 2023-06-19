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
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.slowchat.R;
import com.slowchat.contact.domain.ContactModel;

public class DialogContact {
    private final MaterialAlertDialogBuilder builder;
    private final TextInputEditText idEditText;
    private final TextInputEditText usernameEditText;

    private final boolean isNewContact;

    public DialogContact(@NonNull Context context, ContactModel contact, DialogSubmitListener listener) {
        isNewContact = contact == null || contact.getUsername() == null;

        builder = new MaterialAlertDialogBuilder(context);
        builder.setTitle(isNewContact ? R.string.add_contact : R.string.edit_contact);

        View dialogView = View.inflate(context, R.layout.dialog_contact, null);
        builder.setView(dialogView);

        idEditText = dialogView.findViewById(R.id.dialog_contact_id);
        usernameEditText = dialogView.findViewById(R.id.dialog_contact_username);

        if (contact != null) {
            if (contact.getIdContact() != null) {
                idEditText.setEnabled(false);
                idEditText.setText(contact.getIdContact());
            }

            if (contact.getUsername() != null) {
                usernameEditText.setText(contact.getUsername());
            }
        }

        builder.setPositiveButton(isNewContact ? R.string.add : R.string.edit, (dialogInterface, i) -> {
            String id = String.valueOf(idEditText.getText()).trim();
            String username = String.valueOf(usernameEditText.getText()).trim();

            listener.onSubmit(new ContactModel(id, username));
        });

        builder.setNegativeButton(R.string.cancel, null);
    }

    public void show() {
        AlertDialog dialog = builder.show();

        Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        if (isNewContact) {
            positiveButton.setEnabled(false);
        }

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                boolean isIdEmpty = String.valueOf(idEditText.getText()).trim().isEmpty();
                boolean isUsernameEmpty = String.valueOf(usernameEditText.getText()).trim().isEmpty();

                boolean canSubmit = !isIdEmpty && !isUsernameEmpty;
                positiveButton.setEnabled(canSubmit);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        idEditText.addTextChangedListener(textWatcher);
        usernameEditText.addTextChangedListener(textWatcher);

        usernameEditText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (positiveButton.isEnabled() && i == EditorInfo.IME_ACTION_DONE) {
                return positiveButton.callOnClick();
            }

            return false;
        });
    }
}

