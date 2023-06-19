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
import static com.slowchat.uniqueID.domain.UniqueID.reuseOldUniqueID;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.slowchat.R;
import com.slowchat.appdata.domain.AppDataViewModel;

import java.security.NoSuchAlgorithmException;

public class DialogUniqueIDChangeDevice extends MaterialAlertDialogBuilder {

    public DialogUniqueIDChangeDevice(@NonNull Context context) {
        super(context);
        View view = View.inflate(context, R.layout.dialog_uniqueid_change_device, null);

        TextView oldSalt = view.findViewById(R.id.dialog_uniqueid_old_salt);
        oldSalt.setText(AppDataViewModel.getAppDataModel().getSalt());

        EditText passwordToEncrypt = view.findViewById(R.id.dialog_uniqueid_old_password);
        EditText salt = view.findViewById(R.id.dialog_uniqueid_salt);
        setPositiveButton(R.string.confirm_unique_id_change_device, (dialogInterface, i) -> {
            try {
                reuseOldUniqueID(String.valueOf(passwordToEncrypt.getText()), String.valueOf(salt.getText()));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        });

        setView(view);
    }
}
