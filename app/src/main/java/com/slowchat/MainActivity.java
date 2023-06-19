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
package com.slowchat;

import static com.slowchat.letterbox.domain.LetterBoxViewModel.isALetterBox;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.slowchat.appdata.service.AppDataService;
import com.slowchat.appdata.ui.AppDataActivity;
import com.slowchat.bluetooth.ui.FragmentShare;
import com.slowchat.uniqueID.ui.DialogInitUniqueID;
import com.slowchat.uniqueID.ui.DialogQrCode;
import com.slowchat.utilities.database.AppDatabase;
import com.slowchat.utilities.sharedpreferences.SharedPreferences;

import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    public static boolean refreshMainActivity = false;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(findViewById(R.id.toolbar));
        setupInsets();

        AppDatabase.setAppDatabase(getApplicationContext());
        SharedPreferences.setSharedPreferences(getApplicationContext());

        setupFragments();

        if (isALetterBox()) {
            bottomNavigationView.setVisibility(View.GONE);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, new FragmentShare());
            transaction.commit();
        }
        if (AppDataService.getIdMachine() == null) {
            DialogInitUniqueID dialog = null;
            try {
                dialog = new DialogInitUniqueID(this);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            dialog.show();
        }
    }

    private void setupInsets() {
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        View appbarLayout = findViewById(R.id.appbar_layout);

        ViewCompat.setOnApplyWindowInsetsListener(appbarLayout, (v, insets) -> {
            Insets i = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, i.top, 0, 0);

            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (refreshMainActivity) {
            if (isALetterBox()) {
                bottomNavigationView.setVisibility(View.GONE);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, new FragmentShare());
                transaction.commit();
            } else {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
            refreshMainActivity = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_navigation:
                Intent settingsActivityIntent = new Intent(this, AppDataActivity.class);
                startActivity(settingsActivityIntent);
                break;
            case R.id.qrcode_navigation:
                DialogQrCode dialogQRCODE = new DialogQrCode(this);
                dialogQRCODE.show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupFragments() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            bottomNavigationView = findViewById(R.id.bottom_navigation);
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
        }
    }
}
