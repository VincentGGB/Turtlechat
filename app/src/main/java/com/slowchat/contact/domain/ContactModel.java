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

public class ContactModel implements Comparable<ContactModel>{

    private String idContact;
    private String username;

    public ContactModel() {
    }

    public ContactModel(com.slowchat.contact.service.ContactModel model) {
        this.idContact = model.getIdContact();
        this.username = model.getUsername();
    }

    public ContactModel(String idContact, String username) {
        this.idContact = idContact;
        this.username = username;
    }

    public String getIdContact() {
        return idContact;
    }

    public void setIdContact(String idContact) {
        this.idContact = idContact;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public com.slowchat.contact.service.ContactModel mapObjectToDbObject (){
        com.slowchat.contact.service.ContactModel model = new com.slowchat.contact.service.ContactModel();
        model.setIdContact(this.idContact);
        model.setUsername(this.username);
        return model;
    }

    @Override
    public int compareTo(ContactModel contactModel) {
        return this.username.toLowerCase().compareTo(contactModel.getUsername().toLowerCase());
    }
}
