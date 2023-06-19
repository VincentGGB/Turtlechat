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
package com.slowchat.unit.contact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.slowchat.contact.service.ContactDAO;
import com.slowchat.contact.service.ContactModel;
import com.slowchat.utilities.database.AppDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DataBaseContactUnitTest {

    private AppDatabase db;
    private ContactDAO dao;


    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();

        db = Room.databaseBuilder(context, AppDatabase.class, "test")
                .allowMainThreadQueries()
                .build();

        initTest();
        dao = db.getContactDAO();
    }


    @After
    public void closeDb() {
        initTest();
        db.close();
    }

    @Test
    public void databaseShouldBeOpened() {
        assertTrue(db.isOpen());
    }

    @Test
    public void shouldGetOneById() {
        initTest();

        //GIVEN
        initTest();
        String idSeached = "MAC2";
        insertXRows(5);

        //WHEN
        ContactModel contactDb = dao.getContactById(idSeached);

        //THEN
        assertEquals(idSeached, contactDb.getIdContact());
    }

    @Test
    public void shouldBeUpdated() {
        initTest();

        //GIVEN
        int row = 0;
        ContactModel contact = createTestRow(row);
        dao.insert(contact);

        String newUsername = "EDIT";
        contact.setUsername(newUsername);

        //WHEN
        int nbLineUpdated = dao.update(contact);
        ContactModel contactDb = dao.getContactById("MAC" + row);

        //THEN
        assertEquals(contact.getIdContact(), contactDb.getIdContact());
        assertEquals(newUsername, contactDb.getUsername());
        assertEquals(nbLineUpdated, 1);
    }

    @Test
    public void shouldBeDeleted() {
        initTest();

        //GIVEN
        int row = 0;
        ContactModel contact = createTestRow(row);
        dao.insert(contact);

        //WHEN
        int nbLineDeleted = dao.delete(contact);
        ContactModel contactDb = dao.getContactById("MAC" + row);

        //THEN
        assertNull(contactDb);
        assertEquals(nbLineDeleted, 1);
    }

    @Test
    public void shouldGetList() {
        initTest();

        //GIVEN
        int nb_row = 10;
        List<ContactModel> contacts = insertXRows(nb_row);

        //WHEN
        List<ContactModel> contactsDb = dao.getContacts();

        //THEN
        for (int i = 0; i < nb_row; i++) {
            assertEquals(contacts.get(i).getIdContact(), contactsDb.get(i).getIdContact());
        }
    }

    private ContactModel createTestRow(int i) {
        ContactModel contact = new ContactModel();
        contact.setIdContact("MAC" + i);
        contact.setUsername("username" + i);
        return contact;
    }


    private List<ContactModel> insertXRows(int x) {
        ContactModel contact;
        List<ContactModel> contacts = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            contact = createTestRow(i);
            contacts.add(contact);
            dao.insert(contact);
        }
        return contacts;
    }

    private void initTest() {
        db.clearAllTables();
    }
}
