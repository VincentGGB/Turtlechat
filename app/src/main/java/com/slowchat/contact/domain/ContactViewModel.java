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
package com.slowchat.contact.domain;

import com.slowchat.contact.service.ContactService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactViewModel {
    Map<String, ContactModel> contacts = new HashMap<>();

    public ContactViewModel() {
        setContactsFromDb();
    }

    public boolean setContactsFromDb() {
        ContactService contactService = new ContactService();
        List<com.slowchat.contact.service.ContactModel> contactModels = contactService.getContacts();
        if (contactModels == null) {
            return false;
        }
        this.contacts.clear();
        for (com.slowchat.contact.service.ContactModel model : contactModels) {
            this.contacts.put(model.getIdContact(), new ContactModel(model));
        }
        return true;
    }

    public List<ContactModel> getContacts() {
        List<ContactModel> contacts = new ArrayList<>(this.contacts.values());
        Collections.sort(contacts);
        return contacts;
    }

    public ContactModel getContact(String idContact) {
        return contacts.get(idContact);
    }

    public int getPosition(ContactModel contactModel) {
        List<ContactModel> contacts = new ArrayList<>(this.contacts.values());
        Collections.sort(contacts);
        int i = 0;
        for (ContactModel model: contacts) {
            int tempo = model.compareTo(contactModel);
            if(model.compareTo(contactModel)>=0)
                return i;
            i++;
        }
        return i;
    }

    public boolean addContact(ContactModel contactModel) {
        ContactService contactService = new ContactService();
        if (contactService.insertContact(contactModel.mapObjectToDbObject())) {
            this.contacts.put(contactModel.getIdContact(), contactModel);
            return true;
        }
        return false;
    }

    public boolean updateContact(ContactModel contactModel) {
        ContactService contactService = new ContactService();
        if (contactService.updateContact(contactModel.mapObjectToDbObject())) {
            this.contacts.put(contactModel.getIdContact(), contactModel);
            return true;
        }
        return false;
    }

    public boolean removeContact(ContactModel contactModel) {
        ContactService contactService = new ContactService();
        if (contactService.deleteContact(contactModel.mapObjectToDbObject())) {
            this.contacts.remove(contactModel.getIdContact());
            return true;
        }
        return false;
    }
}
