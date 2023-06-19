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
package com.slowchat.message.domain;

import com.slowchat.message.domain.models.ContactModel;
import com.slowchat.message.domain.models.MessageModel;

import java.util.ArrayList;
import java.util.List;

public class ChatModel {
    private List<ContactModel> contacts;
    private List<MessageModel> messages;

    public ChatModel() {
        contacts = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public List<ContactModel> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactModel> contacts) {
        this.contacts = contacts;
    }

    public void setContactsFromService(List<com.slowchat.contact.service.ContactModel> contacts) {
        this.contacts.clear();
        for (com.slowchat.contact.service.ContactModel model : contacts) {
            this.contacts.add(new ContactModel(model));
        }
    }

    public List<MessageModel> getMessages() {
        return this.messages;
    }

    public void setMessages(List<MessageModel> messages) {
        this.messages.clear();
        this.messages = messages;
    }

    public void addMessage(MessageModel message) {
        this.messages.add(message);
    }

    public void deleteMessage(MessageModel message) {
        this.messages.remove(message);
    }

    public void setMessagesFromService(List<com.slowchat.message.service.MessageModel> messages) {
        this.messages.clear();

        for (com.slowchat.message.service.MessageModel message : messages) {
            this.messages.add(new MessageModel(message));
        }
    }
}
