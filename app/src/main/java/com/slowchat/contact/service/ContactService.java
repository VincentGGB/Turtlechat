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
package com.slowchat.contact.service;

import com.slowchat.utilities.database.AppDatabase;

import java.util.List;

public class ContactService {
    private static final ContactDAO contactDAO = AppDatabase.getAppDatabase().getContactDAO();

    public static boolean insertContact(ContactModel contactModel) {
        return contactDAO.insert(contactModel)[0] != -1;
    }

    public static List<ContactModel> getContacts() {
        return contactDAO.getContacts();
    }

    public static boolean updateContact(ContactModel contactModel) {
        return contactDAO.update(contactModel) == 1;
    }

    public static boolean deleteContact(ContactModel contactModel) {
        return contactDAO.delete(contactModel) == 1;
    }
}
