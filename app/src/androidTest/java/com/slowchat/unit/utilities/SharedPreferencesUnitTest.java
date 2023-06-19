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

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.slowchat.utilities.sharedpreferences.SharedPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Set;
import java.util.TreeSet;

@RunWith(AndroidJUnit4.class)
public class SharedPreferencesUnitTest {
    SharedPreferences sharedPreferences = new SharedPreferences();

    @Before
    public void beforeTest() {
        Context context = ApplicationProvider.getApplicationContext();
        SharedPreferences.setSharedPreferences(context);
    }

    @Test
    public void shouldSetAndGetBoolean() {
        //GIVEN
        boolean data = true;

        //WHEN
        sharedPreferences.setData("test", data);
        boolean test = (boolean) sharedPreferences.getData("test", SharedPreferences.EnumType.BOOLEAN);

        //THEN
        Assert.assertNotEquals(data, false);
        Assert.assertEquals(data, test);
    }

    @Test
    public void shouldSetAndGetInt() {
        //GIVEN
        int data = 100;

        //WHEN
        sharedPreferences.setData("test", data);
        int test = (int) sharedPreferences.getData("test", SharedPreferences.EnumType.INT);

        //THEN
        Assert.assertNotEquals(data, -1);
        Assert.assertEquals(data, test);
    }

    @Test
    public void shouldSetAndGetLong() {
        //GIVEN
        long data = 10000000000L;

        //WHEN
        sharedPreferences.setData("test", data);
        long test = (long) sharedPreferences.getData("test", SharedPreferences.EnumType.LONG);

        //THEN
        Assert.assertNotEquals(data, -1);
        Assert.assertEquals(data, test);
    }

    @Test
    public void shouldSetAndGetFloat() {
        //GIVEN
        float data = 5.5f;

        //WHEN
        sharedPreferences.setData("test", data);
        float test = (float) sharedPreferences.getData("test", SharedPreferences.EnumType.FLOAT);

        //THEN
        Assert.assertNotEquals(data, -1);
        Assert.assertEquals(data, test, 0f);
    }

    @Test
    public void shouldSetAndGetString() {
        //GIVEN
        String data = "test";

        //WHEN
        sharedPreferences.setData("test", data);
        String test = (String) sharedPreferences.getData("test", SharedPreferences.EnumType.STRING);

        //THEN
        Assert.assertNotEquals(data, null);
        Assert.assertEquals(data, test);
    }

    @Test
    public void shouldSetAndGetStringSet() {
        //GIVEN
        Set<String> data = new TreeSet<>();
        data.add("test1");
        data.add("test2");

        //WHEN
        sharedPreferences.setData("test", data);
        Set<String> test = (Set<String>) sharedPreferences.getData("test", SharedPreferences.EnumType.STRING_SET);

        //THEN
        Assert.assertNotEquals(data, null);
        Assert.assertEquals(data, test);
    }

}