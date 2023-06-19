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
package com.slowchat.letterbox.ui;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.slowchat.R;
import com.slowchat.letterbox.domain.LetterBoxModel;

import java.text.DateFormat;
import java.util.Locale;

public class DialogLetterBox extends MaterialAlertDialogBuilder {

    public DialogLetterBox(@NonNull Context context, LetterBoxModel letterbox) {
        super(context);
        setTitle(R.string.name_dialog_letterbox);
        View view = View.inflate(context, R.layout.dialog_letterbox, null);

        TextView id = view.findViewById(R.id.dialog_letterbox_id);
        id.setText(letterbox.getIdLetterBox());

        TextView state = view.findViewById(R.id.dialog_letterbox_state);
        state.setText(String.valueOf(letterbox.getState()));

        TextView latitude = view.findViewById(R.id.dialog_letterbox_latitude);
        latitude.setText(String.valueOf(letterbox.getLatitude()));

        TextView longitude = view.findViewById(R.id.dialog_letterbox_longitude);
        longitude.setText(String.valueOf(letterbox.getLongitude()));

        TextView lastUpdated = view.findViewById(R.id.dialog_letterbox_lastUpdated);
        DateFormat f = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        String date = f.format(letterbox.getLastUpdated());
        lastUpdated.setText(date);

        TextView needAlim = view.findViewById(R.id.dialog_letterbox_needAlim);
        needAlim.setText(String.valueOf(letterbox.getNeedAlim()));

        setNeutralButton(R.string.edit_button_letterbox, (dialogInterface, i) -> {
            DialogEditLetterBox editLetterBox = new DialogEditLetterBox(context,letterbox);
            editLetterBox.show();
        });

        setView(view);
    }

}
