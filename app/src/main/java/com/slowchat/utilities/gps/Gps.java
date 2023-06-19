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
package com.slowchat.utilities.gps;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.location.LocationListenerCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.slowchat.R;

public class Gps {
    private static final int REQUEST_GPS = 200;

    private static final long MIN_TIME = 6000;
    private static final long MIN_DISTANCE = 0;

    private final Activity activity;
    private final LocationManager locationManager;
    private static boolean isGpsSupported;

    public Gps(Activity activity) {
        this.activity = activity;

        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        isGpsSupported = locationManager != null;
        if (!isGpsSupported) {
            new AlertDialog.Builder(activity)
                    .setTitle(R.string.no_gps)
                    .setMessage(R.string.no_gps_description)
                    .setPositiveButton(android.R.string.ok, null)
                    .create()
                    .show();
        }
    }

    public boolean isGpsAllowed() {
        return ActivityCompat.checkSelfPermission(activity, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(activity, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressLint("MissingPermission")
    public @Nullable Location getLocation() {
        if (!isGpsSupported) return null;

        if (!isGpsAllowed()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, ACCESS_FINE_LOCATION)) {
                new MaterialAlertDialogBuilder(activity)
                        .setTitle(R.string.authorize_gps)
                        .setMessage(R.string.authorize_gps_description)
                        .setPositiveButton(android.R.string.ok, (dialogInterface, i) ->
                                ActivityCompat.requestPermissions(activity, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, REQUEST_GPS))
                        .setNegativeButton(R.string.cancel, null)
                        .create()
                        .show();
                return null;
            }

            ActivityCompat.requestPermissions(activity, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, REQUEST_GPS);
            return null;
        }

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            new MaterialAlertDialogBuilder(activity)
                    .setTitle(R.string.enable_gps)
                    .setMessage(R.string.enable_gps_description)
                    .setPositiveButton(android.R.string.yes, (dialogInterface, i) ->
                            activity.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    )
                    .setNegativeButton(android.R.string.no, null)
                    .setCancelable(false)
                    .create()
                    .show();
            return null;
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) return location;

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, (LocationListenerCompat) l -> {});
        return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }
}
