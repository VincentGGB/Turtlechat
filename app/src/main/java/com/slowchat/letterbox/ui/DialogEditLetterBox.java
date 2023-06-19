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

import static com.slowchat.letterbox.domain.LetterBoxViewModel.editLetterBox;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.slowchat.R;
import com.slowchat.letterbox.domain.LetterBoxModel;
import com.slowchat.letterbox.domain.StateType;


public class DialogEditLetterBox extends MaterialAlertDialogBuilder {
    public DialogEditLetterBox(@NonNull Context context, LetterBoxModel letterbox) {
        super(context);
        setTitle(R.string.name_dialog_edit_letterbox);
        View view = View.inflate(context, R.layout.dialog_edit_letterbox, null);

        TextView id = view.findViewById(R.id.dialog_letterbox_id);
        id.setText(letterbox.getIdLetterBox());

        EditText latitude = view.findViewById(R.id.dialog_letterbox_latitude);
        latitude.setText(String.valueOf(letterbox.getLatitude()));

        TextView longitude = view.findViewById(R.id.dialog_letterbox_longitude);
        longitude.setText(String.valueOf(letterbox.getLongitude()));

        MaterialSwitch switchNeedAlim = view.findViewById(R.id.dialog_letterbox_switch_need_alim);
        switchNeedAlim.setChecked(letterbox.getNeedAlim());
        MaterialSwitch switchState = view.findViewById(R.id.dialog_letterbox_switch_state);
        switchState.setChecked(letterbox.getState() == StateType.ENABLED);

        setPositiveButton(R.string.confirm_edition_letterbox, (dialogInterface, i) -> {
            boolean flagFormIsValid = true;

            String identifiantForm = id.getText().toString();
            String latitudeForm = latitude.getText().toString();
            String longitudeForm = longitude.getText().toString();

            if (identifiantForm.isEmpty()) {
                flagFormIsValid = false;
            }
            if (latitudeForm.isEmpty()) {
                flagFormIsValid = false;
            }
            if (longitudeForm.isEmpty()) {
                flagFormIsValid = false;
            }

            if (flagFormIsValid) {
                if (switchState.isChecked()) {
                    letterbox.setState(StateType.ENABLED);
                } else letterbox.setState(StateType.DISABLED);
                letterbox.setLatitude(Float.parseFloat(latitudeForm));
                letterbox.setLongitude(Float.parseFloat(longitudeForm));
                letterbox.setNeedAlim(switchNeedAlim.isChecked());
                editLetterBox(letterbox);
            }
        });

        setView(view);
    }
}
