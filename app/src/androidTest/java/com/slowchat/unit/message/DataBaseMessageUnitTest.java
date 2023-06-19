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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.slowchat.message.service.MessageDAO;
import com.slowchat.message.service.MessageModel;
import com.slowchat.utilities.database.AppDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DataBaseMessageUnitTest {

    private AppDatabase db;
    private MessageDAO dao;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();

        db = Room.databaseBuilder(context, AppDatabase.class, "test")
                .allowMainThreadQueries()
                .build();

        db.clearAllTables();
        dao = db.getMessageDAO();
    }

    @After
    public void closeDb() {
        db.clearAllTables();
        db.close();
    }

    @Test
    public void databaseShouldBeOpened() {
        assertTrue(db.isOpen());
    }

    @Test
    public void shouldBeUpdated() {
        initTest();

        //GIVEN
        MessageModel message = createTestRow(0);
        dao.insert(message);

        String newMessage = "EDIT";
        message.setMessage(newMessage);

        //WHEN
        int nbLineUpdated = dao.update(message);
        MessageModel messageDb = dao.getMessages().get(0);

        //THEN
        assertEquals(newMessage, messageDb.getMessage());
        assertEquals(nbLineUpdated, 1);

    }

    @Test
    public void shouldBeDeleted() {
        initTest();

        //GIVEN
        MessageModel message = createTestRow(0);
        dao.insert(message);

        //WHEN
        int nbLineDeleted = dao.delete(message);
        MessageModel messageDb = dao.getMessages().get(0);

        //THEN
        assertNull(messageDb);
        assertEquals(nbLineDeleted, 1);
    }

    @Test
    public void shouldGetList() {
        initTest();

        //GIVEN
        int nb_row = 10;
        List<MessageModel> messages = insertXRows(nb_row);

        //WHEN
        List<MessageModel> contactsBDD = dao.getMessages();

        //THEN
        for (int i = 0; i < nb_row; i++) {
            assertEquals(messages.get(i), contactsBDD.get(i));
        }
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


    private List<MessageModel> insertXRows(int x) {
        MessageModel message;
        List<MessageModel> messages = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            message = createTestRow(i);
            dao.insert(message);
            messages.add(message);
        }
        return messages;
    }

    private void initTest() {
        db.clearAllTables();
        dao.resetAutoIncrement();
    }
}
