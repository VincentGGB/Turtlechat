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
package com.slowchat.unit.appdata;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.slowchat.appdata.service.AppDataService;
import com.slowchat.utilities.sharedpreferences.SharedPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AppDataServiceUnitTest {

    @Before
    public void beforeTest() {
        Context context = ApplicationProvider.getApplicationContext();
        SharedPreferences.setSharedPreferences(context);
        AppDataService.initAppDataService("IDUNIQUE");
    }

    @Test
    public void shouldSetAndGetIdMachine() {
        //GIVEN
        String idMachine = "AAMMMAMMMAMMA";

        //WHEN
        AppDataService.setIdMachine(idMachine);
        String output = AppDataService.getIdMachine();

        //THEN
        Assert.assertNotNull(output);
        Assert.assertEquals(idMachine, output);
    }

    @Test
    public void shouldSetAndGetTheme() {
        //GIVEN
        String theme = "default";

        //WHEN
        AppDataService.setTheme(theme);
        String output = AppDataService.getTheme();

        //THEN
        Assert.assertNotNull(output);
        Assert.assertEquals(theme, output);
    }

    @Test
    public void shouldSetAndGetMode() {
        //GIVEN
        boolean mode = true;

        //WHEN
        AppDataService.setLetterBoxMode(mode);
        boolean output = AppDataService.isLetterBox();

        //THEN
        Assert.assertEquals(mode, output);
    }

    @Test
    public void shouldSetAndGetMemorySizeCarto() {
        //GIVEN
        int memorySize = 10;

        //WHEN
        AppDataService.setMemorySizeCarto(memorySize);
        int output = AppDataService.getMemorySizeCarto();

        //THEN
        Assert.assertNotEquals(memorySize, -1);
        Assert.assertEquals(memorySize, output);
    }

    @Test
    public void shouldSetAndGetMemorySizeMap() {
        //GIVEN
        int memorySize = 10;

        //WHEN
        AppDataService.setMemorySizeMap(memorySize);
        int output = AppDataService.getMemorySizeMap();

        //THEN
        Assert.assertNotEquals(memorySize, -1);
        Assert.assertEquals(memorySize, output);
    }

    @Test
    public void shouldSetAndGetMemorySizeMessage() {
        //GIVEN
        int memorySize = 10;

        //WHEN
        AppDataService.setMemorySizeMessage(memorySize);
        int output = AppDataService.getMemorySizeMessage();

        //THEN
        Assert.assertNotEquals(memorySize, -1);
        Assert.assertEquals(memorySize, output);
    }

    @Test
    public void shouldSetAndGetSalt() {
        //GIVEN
        String salt = "1234567890987654321";

        //WHEN
        AppDataService.setSalt(salt);
        String output = AppDataService.getSalt();

        //THEN
        Assert.assertEquals(salt, output);
    }
}