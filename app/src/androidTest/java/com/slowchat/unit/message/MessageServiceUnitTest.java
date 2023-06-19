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
package com.slowchat.unit.message;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.slowchat.message.service.MessageModel;
import com.slowchat.message.service.MessageService;
import com.slowchat.utilities.database.AppDatabase;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MessageServiceUnitTest {
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();

        AppDatabase.setAppDatabase(context);
        db = AppDatabase.getAppDatabase();
        initTest();
    }

    @Test
    public void shouldReturnTrueWhenInserting() {
        initTest();

        //GIVEN
        MessageModel message = createTestRow(0);

        //WHEN
        long ouput = MessageService.insertMessage(message);

        //THEN
        assertNotEquals(ouput, -1L);
    }

    @Test
    public void shouldReturnTrueWhenUpdating() {
        initTest();

        //GIVEN
        MessageModel message = createTestRow(0);
        long id = MessageService.insertMessage(message);

        String newData = "EDIT";
        message.setMessage(newData);

        //WHEN
        boolean ouput = MessageService.updateMessage(message);

        //THEN
        assertTrue(ouput);
    }

    @Test
    public void shouldNotReturnTrueWhenUpdating() {
        initTest();

        //GIVEN
        MessageModel message = createTestRow(0);

        //WHEN
        boolean ouput = MessageService.updateMessage(message);

        //THEN
        assertFalse(ouput);
    }

    @Test
    public void shouldReturnTrueWhenDeleting() {
        initTest();

        //GIVEN
        MessageModel message = createTestRow(0);
        long id = MessageService.insertMessage(message);

        String newData = "EDIT";
        message.setMessage(newData);

        //WHEN
        boolean ouput = MessageService.deleteMessage(message);

        //THEN
        assertTrue(ouput);
    }

    @Test
    public void shouldNotReturnTrueWhenDeleting() {
        initTest();

        //GIVEN
        MessageModel message = createTestRow(0);

        //WHEN
        boolean ouput = MessageService.deleteMessage(message);

        //THEN
        assertFalse(ouput);
    }

    @Test
    public void shouldDeleteOnlyOldMessages() {
        initTest();
        //GIVEN
        for (int i = 0; i < 23; i++) {
            MessageModel message = createTestRow(i);
            MessageService.insertMessage(message);
        }
        List<MessageModel> messages = MessageService.getMessages();

        //WHEN
        int ouput = MessageService.deleteOldMessages();

        //THEN
        assertEquals(ouput, 14);
    }

    private void initTest() {
        db.clearAllTables();
    }

    private MessageModel createTestRow(int i) {
        MessageModel message = new MessageModel();
        message.setReceiver("MAC" + i % 3);
        message.setSender("MAC" + (i + 1) % 3);
        message.setReceptionDate(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.set((int) (2000L + i), 1, 1, 0, 0, 0);
        message.setSentDate(calendar.getTime());
        message.setMessage("Message" + i);
        return message;
    }
}
