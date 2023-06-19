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
package com.slowchat.uniqueID.ui;

import static com.slowchat.uniqueID.domain.UniqueID.createUniqueID;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.slowchat.R;

import java.security.NoSuchAlgorithmException;

public class DialogInitUniqueID extends MaterialAlertDialogBuilder {

    public DialogInitUniqueID(@NonNull Context context) throws NoSuchAlgorithmException {
        super(context);
        View view = View.inflate(context, R.layout.dialog_uniqueid_init, null);
        setMessage(R.string.message_welcome_init_unique_id);
        EditText passwordToEncrypt = view.findViewById(R.id.dialog_uniqueid_init_password);
        setCancelable(false);
        setPositiveButton(R.string.confirm_init_unique_id, (dialogInterface, i) -> {
            try {
                createUniqueID(String.valueOf(passwordToEncrypt.getText()));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        });
        setView(view);
    }
}
