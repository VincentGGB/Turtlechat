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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.slowchat.contact.service.ContactModel;
import com.slowchat.contact.service.ContactService;
import com.slowchat.utilities.database.AppDatabase;

import org.junit.Before;
import org.junit.Test;

public class ContactServiceUnitTest {

    private AppDatabase db;
    private ContactService contactService;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();

        AppDatabase.setAppDatabase(context);
        db = AppDatabase.getAppDatabase();
        initTest();

        contactService = new ContactService();
    }

    @Test
    public void shouldReturnTrueWhenInserting() {
        initTest();

        //GIVEN
        ContactModel contact = createTestRow(0);

        //WHEN
        boolean ouput = contactService.insertContact(contact);

        //THEN
        assertTrue(ouput);
    }

    @Test
    public void shouldNotReturnTrueWhenInserting() {
        initTest();

        //GIVEN
        ContactModel contact = createTestRow(0);
        contactService.insertContact(contact);

        //WHEN
        boolean ouput = contactService.insertContact(contact);

        //THEN
        assertFalse(ouput);
    }

    @Test
    public void shouldReturnTrueWhenUpdating() {
        initTest();

        //GIVEN
        ContactModel contact = createTestRow(0);
        contactService.insertContact(contact);

        String newUsername = "EDIT";
        contact.setUsername(newUsername);

        //WHEN
        boolean ouput = contactService.updateContact(contact);

        //THEN
        assertTrue(ouput);
    }

    @Test
    public void shouldNotReturnTrueWhenUpdating() {
        initTest();

        //GIVEN
        ContactModel contact = createTestRow(0);

        //WHEN
        boolean ouput = contactService.updateContact(contact);

        //THEN
        assertFalse(ouput);
    }

    @Test
    public void shouldReturnTrueWhenDeleting() {
        initTest();

        //GIVEN
        ContactModel contact = createTestRow(0);
        contactService.insertContact(contact);

        String newUsername = "EDIT";
        contact.setUsername(newUsername);

        //WHEN
        boolean ouput = contactService.deleteContact(contact);

        //THEN
        assertTrue(ouput);
    }

    @Test
    public void shouldNotReturnTrueWhenDeleting() {
        initTest();

        //GIVEN
        ContactModel contact = createTestRow(0);

        //WHEN
        boolean ouput = contactService.deleteContact(contact);

        //THEN
        assertFalse(ouput);
    }

    private void initTest() {
        db.clearAllTables();
    }

    private ContactModel createTestRow(int i) {
        ContactModel contact = new ContactModel();
        contact.setIdContact("MAC" + i);
        contact.setUsername("username" + i);
        return contact;
    }
}
