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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


import android.content.Context;

import androidx.test.core.app.ApplicationProvider;


import com.slowchat.appdata.domain.AppDataViewModel;
import com.slowchat.appdata.service.AppDataService;
import com.slowchat.utilities.sharedpreferences.SharedPreferences;

import org.junit.Before;
import org.junit.Test;

public class AppDataDomainUnitTest {

    private AppDataViewModel appDataViewModel;

    @Before
    public void beforeTest() {
        Context context = ApplicationProvider.getApplicationContext();
        SharedPreferences.setSharedPreferences(context);
        initTest();
        appDataViewModel = new AppDataViewModel();
    }

    @Test
    public void shouldGetAllData(){
        //GIVEN
        initTest();

        //WHEN
        AppDataViewModel output = new AppDataViewModel();

        //THEN
        assertNotNull(output);
        assertEquals("IDUNIQUE",output.getAppDataModel().getIdMachine());
        assertEquals("default",output.getAppDataModel().getTheme());
        assertFalse(output.getAppDataModel().isLetterBox());
        assertEquals(512, output.getAppDataModel().getMemorySizeMap());
        assertEquals(512, output.getAppDataModel().getMemorySizeCarto());
        assertEquals(512, output.getAppDataModel().getMemorySizeMessage());
        assertEquals("default", output.getAppDataModel().getSalt());
    }

    @Test
    public void shouldSetTheme(){
        //GIVEN
        initTest();
        String newTheme = "Dark";

        //WHEN
        appDataViewModel.setTheme(newTheme);
        AppDataViewModel output = new AppDataViewModel();

        //THEN
        assertNotNull(output);
        assertEquals("IDUNIQUE",output.getAppDataModel().getIdMachine());
        assertEquals(newTheme,output.getAppDataModel().getTheme());
        assertFalse(output.getAppDataModel().isLetterBox());
        assertEquals(512, output.getAppDataModel().getMemorySizeMap());
        assertEquals(512, output.getAppDataModel().getMemorySizeCarto());
        assertEquals(512, output.getAppDataModel().getMemorySizeMessage());
        assertEquals("default", output.getAppDataModel().getSalt());
    }

    @Test
    public void shouldSetLetterBoxMode(){
        //GIVEN
        initTest();
        boolean isLetterBox = true;

        //WHEN
        appDataViewModel.setLetterBoxMode(isLetterBox);
        AppDataViewModel output = new AppDataViewModel();

        //THEN
        assertNotNull(output);
        assertEquals("IDUNIQUE",output.getAppDataModel().getIdMachine());
        assertEquals("default",output.getAppDataModel().getTheme());
        assertEquals(isLetterBox,output.getAppDataModel().isLetterBox());
        assertEquals(512, output.getAppDataModel().getMemorySizeMap());
        assertEquals(512, output.getAppDataModel().getMemorySizeCarto());
        assertEquals(512, output.getAppDataModel().getMemorySizeMessage());
        assertEquals("default", output.getAppDataModel().getSalt());
    }

    @Test
    public void shouldSetMemorySizeMap(){
        //GIVEN
        initTest();
        int newSize = 1024;

        //WHEN
        appDataViewModel.setMemorySizeMap(newSize);
        AppDataViewModel output = new AppDataViewModel();

        //THEN
        assertNotNull(output);
        assertEquals("IDUNIQUE",output.getAppDataModel().getIdMachine());
        assertEquals("default",output.getAppDataModel().getTheme());
        assertFalse(output.getAppDataModel().isLetterBox());
        assertEquals(newSize, output.getAppDataModel().getMemorySizeMap());
        assertEquals(512, output.getAppDataModel().getMemorySizeCarto());
        assertEquals(512, output.getAppDataModel().getMemorySizeMessage());
        assertEquals("default", output.getAppDataModel().getSalt());
    }

    @Test
    public void shouldSetMemorySizeCarto(){
        //GIVEN
        initTest();
        int newSize = 1024;

        //WHEN
        appDataViewModel.setMemorySizeCarto(newSize);
        AppDataViewModel output = new AppDataViewModel();

        //THEN
        assertNotNull(output);
        assertEquals("IDUNIQUE",output.getAppDataModel().getIdMachine());
        assertEquals("default",output.getAppDataModel().getTheme());
        assertFalse(output.getAppDataModel().isLetterBox());
        assertEquals(512, output.getAppDataModel().getMemorySizeMap());
        assertEquals(newSize, output.getAppDataModel().getMemorySizeCarto());
        assertEquals(512, output.getAppDataModel().getMemorySizeMessage());
        assertEquals("default", output.getAppDataModel().getSalt());
    }

    @Test
    public void shouldSetMemorySizeMessage(){
        //GIVEN
        initTest();
        int newSize = 1024;

        //WHEN
        appDataViewModel.setMemorySizeMessage(newSize);
        AppDataViewModel output = new AppDataViewModel();

        //THEN
        assertNotNull(output);
        assertEquals("IDUNIQUE",output.getAppDataModel().getIdMachine());
        assertEquals("default",output.getAppDataModel().getTheme());
        assertFalse(output.getAppDataModel().isLetterBox());
        assertEquals(512, output.getAppDataModel().getMemorySizeMap());
        assertEquals(512, output.getAppDataModel().getMemorySizeCarto());
        assertEquals(newSize, output.getAppDataModel().getMemorySizeMessage());
        assertEquals("default", output.getAppDataModel().getSalt());
    }

    @Test
    public void shouldSetSalt(){
        //GIVEN
        initTest();
        String newSalt = "SALT";

        //WHEN
        appDataViewModel.setSalt(newSalt);
        AppDataViewModel output = new AppDataViewModel();

        //THEN
        assertNotNull(output);
        assertEquals("IDUNIQUE",output.getAppDataModel().getIdMachine());
        assertEquals("default",output.getAppDataModel().getTheme());
        assertFalse(output.getAppDataModel().isLetterBox());
        assertEquals(512, output.getAppDataModel().getMemorySizeMap());
        assertEquals(512, output.getAppDataModel().getMemorySizeCarto());
        assertEquals(512, output.getAppDataModel().getMemorySizeMessage());
        assertEquals(newSalt, output.getAppDataModel().getSalt());
    }

    private void initTest() {
        AppDataService.setDefaultData("IDUNIQUE");
        AppDataService.setSalt("default");
    }
}
