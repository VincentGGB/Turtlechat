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
package com.slowchat.appdata.ui;

import static com.slowchat.letterbox.domain.LetterBoxViewModel.isALetterBox;
import static com.slowchat.letterbox.domain.LetterBoxViewModel.swapToUser;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.slowchat.MainActivity;
import com.slowchat.R;
import com.slowchat.appdata.service.AppDataService;
import com.slowchat.letterbox.domain.LetterBoxModel;
import com.slowchat.letterbox.ui.DialogCreateLetterBox;
import com.slowchat.uniqueID.ui.DialogUniqueIDChangeDevice;

public class AppDataActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setSupportActionBar(findViewById(R.id.settings_toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        MaterialButton buttonSwitchToLetterBox = findViewById(R.id.settings_change_to_letterbox);

        if (isALetterBox()) {
            buttonSwitchToLetterBox.setText(R.string.settings_become_client);
            becomeUser();
        } else {
            becomeLetterBox();
        }

        changeToAnotherDevice();

        MainActivity.refreshMainActivity = true;
    }

    private void becomeLetterBox() {
        MaterialButton buttonSwitchToLetterBox = findViewById(R.id.settings_change_to_letterbox);
        buttonSwitchToLetterBox.setOnClickListener(l -> {
            LetterBoxModel letterbox = new LetterBoxModel();
            Context context = l.getContext();
            DialogCreateLetterBox dialogCreateLetterBox = new DialogCreateLetterBox(context, letterbox, true);
            dialogCreateLetterBox.setOnDismissListener(dialog -> {
                recreate();
            });
            dialogCreateLetterBox.show();
        });
    }

    private void changeToAnotherDevice() {
        MaterialButton buttonChangeDevice = findViewById(R.id.settings_change_device);
        buttonChangeDevice.setOnClickListener(l -> {
            Context context = l.getContext();
            DialogUniqueIDChangeDevice dialogChangeDevice = new DialogUniqueIDChangeDevice(context);
            dialogChangeDevice.show();
        });
    }

    private void becomeUser() {
        MaterialButton buttonSwitchToLetterBox = findViewById(R.id.settings_change_to_letterbox);
        buttonSwitchToLetterBox.setOnClickListener(l -> {
            swapToUser();
            recreate();
            //TODO: Delete letterbox associé
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
