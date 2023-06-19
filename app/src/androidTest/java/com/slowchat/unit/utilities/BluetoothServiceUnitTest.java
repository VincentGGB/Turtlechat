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
package com.slowchat.unit.utilities;

import android.bluetooth.BluetoothAdapter;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.slowchat.MainActivity;
import com.slowchat.bluetooth.service.BluetoothService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BluetoothServiceUnitTest {
    BluetoothService bluetoothService;
    MainActivity activity;

    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void beforeTest() {
        rule.getScenario().onActivity(activity -> this.activity = activity);
        bluetoothService = new BluetoothService(activity, null);
    }

    @Test
    public void shouldReturnBluetoothState() {
        //GIVEN
        boolean data = BluetoothAdapter.getDefaultAdapter().isEnabled();

        //WHEN
        boolean test = bluetoothService.isBluetoothEnabled();

        //THEN
        Assert.assertEquals(data, test);
    }
}