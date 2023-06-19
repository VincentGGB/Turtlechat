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
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;


import com.slowchat.contact.domain.ContactModel;
import com.slowchat.contact.domain.ContactViewModel;
import com.slowchat.contact.service.ContactDAO;

import com.slowchat.utilities.database.AppDatabase;


import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ContactDomainUnitTest {
    private AppDatabase db;
    private ContactDAO contactDAO;

    private ContactViewModel contactViewModel;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();

        AppDatabase.setAppDatabase(context);

        db = AppDatabase.getAppDatabase();
        contactDAO = db.getContactDAO();
        contactViewModel = new ContactViewModel();
    }

    @Test
    public void shouldGetEmptyListContacts(){
        //GIVEN
        initTest();

        //WHEN
        boolean output = contactViewModel.setContactsFromDb();
        List<ContactModel> contacts = contactViewModel.getContacts();

        //THEN
        assertTrue(output);
        assertEquals(contacts.size(), contactDAO.getContacts().size());
    }

    @Test
    public void shouldGetAllContacts(){
        //GIVEN
        initTest();
        for (int i = 0; i < 6; i++) {
            contactDAO.insert(createTestRow(i));
        }

        //WHEN
        boolean output = contactViewModel.setContactsFromDb();
        List<ContactModel> contacts = contactViewModel.getContacts();

        //THEN
        assertTrue(output);
        assertEquals(contacts.size(), contactDAO.getContacts().size());
    }

    @Test
    public void shouldGetPosition(){
        //GIVEN
        initTest();
        for (int i = 0; i < 6; i++) {
            contactDAO.insert(createTestRow(i));
        }

        //WHEN
        ContactModel model = new ContactModel(createTestRow(3));
        int output = contactViewModel.getPosition(model);

        //THEN
        assertEquals(3, output);
    }

    @Test
    public void shouldAddOneContacts(){
        //GIVEN
        initTest();
        int nbLines = 6;
        for (int i = 0; i < nbLines; i++) {
            contactDAO.insert(createTestRow(i));
        }
        ContactModel contactModel = new ContactModel("MAC10","CSGO");
        contactViewModel.setContactsFromDb();

        //WHEN
        boolean output = contactViewModel.addContact(contactModel);
        List<ContactModel> contacts = contactViewModel.getContacts();


        //THEN
        assertTrue(output);
        assertEquals(contacts.size(), nbLines+1);
        assertEquals(contactDAO.getContacts().size(), nbLines+1);
    }


    @Test
    public void shouldUpdateOneContacts(){
        //GIVEN
        initTest();
        int nbLines = 6;
        for (int i = 0; i < nbLines; i++) {
            contactDAO.insert(createTestRow(i));
        }
        ContactModel contactModel = new ContactModel("MAC0","CSGO");
        contactViewModel.setContactsFromDb();

        //WHEN
        boolean output = contactViewModel.updateContact(contactModel);
        List<ContactModel> contacts = contactViewModel.getContacts();


        //THEN
        assertTrue(output);
        assertEquals(contacts.size(), nbLines);
        for (ContactModel model :contacts) {
            if(model.getIdContact() == contactModel.getIdContact()){
                assertEquals(model.getUsername(), contactModel.getUsername());
            }
        }
        assertEquals(contactDAO.getContacts().size(), nbLines);
        assertEquals(contactDAO.getContactById(contactModel.getIdContact()).getUsername(), contactModel.getUsername());
    }

    @Test
    public void shouldRemoveOneContacts(){
        //GIVEN
        initTest();
        int nbLines = 6;
        for (int i = 0; i < nbLines; i++) {
            contactDAO.insert(createTestRow(i));
        }
        ContactModel contactModel = new ContactModel("MAC0","CSGO");
        contactViewModel.setContactsFromDb();

        //WHEN
        boolean output = contactViewModel.removeContact(contactModel);
        List<ContactModel> contacts = contactViewModel.getContacts();


        //THEN
        assertTrue(output);
        assertEquals(contacts.size(), nbLines-1);
        assertEquals(contactDAO.getContacts().size(), nbLines-1);
    }

    private com.slowchat.contact.service.ContactModel createTestRow(int i) {
        com.slowchat.contact.service.ContactModel contact = new com.slowchat.contact.service.ContactModel();
        contact.setIdContact("MAC" + i);
        contact.setUsername("username" + (10-i));
        return contact;
    }

    private void initTest() {
        db.clearAllTables();
    }
}
