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

import com.slowchat.appdata.service.AppDataService;
import com.slowchat.contact.service.ContactService;
import com.slowchat.message.domain.models.ContactModel;
import com.slowchat.message.domain.models.MessageModel;
import com.slowchat.message.service.MessageService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatViewModel {
    private final ChatModel chat = new ChatModel();

    public ChatViewModel() {
        this.getAllInformationFromDb();
    }

    public ChatModel getChat() {
        return chat;
    }

    public boolean getAllInformationFromDb() {
        List<com.slowchat.message.service.MessageModel> messageModels = MessageService.getMessages();
        List<com.slowchat.contact.service.ContactModel> contactModels = ContactService.getContacts();

        if (messageModels == null || contactModels == null) {
            return false;
        }

        chat.setMessagesFromService(messageModels);
        chat.setContactsFromService(contactModels);

        return true;
    }

    public boolean sendMessage(MessageModel message) {
        if (MessageService.insertMessage(message.mapObjectToDbObject()) == -1L) {
            return false;
        }

        chat.addMessage(message);
        return true;
    }

    public boolean removeMessage(MessageModel message) {
        if (!MessageService.deleteMessage(message.mapObjectToDbObject())) {
            return false;
        }

        chat.deleteMessage(message);
        return true;
    }

    public static List<MessageModel> getMessages() {
        List<MessageModel> messages = new ArrayList<>();

        for (com.slowchat.message.service.MessageModel message : MessageService.getMessages()) {
            messages.add(new MessageModel(message));
        }

        return messages;
    }

    public List<MessageModel> getMessagesOfConversation(String user) {
        List<MessageModel> messages = new ArrayList<>();
        String idMachine = AppDataService.getIdMachine();

        for (MessageModel message : this.chat.getMessages()) {
            if ((message.isFor(idMachine) && message.isBy(user)) ||
                    (message.isBy(idMachine) && message.isFor(user))) {
                messages.add(message);
            }
        }

        Collections.sort(messages);
        return messages;
    }

    public List<String> getConversationContacts() {
        List<String> contacts = new ArrayList<>();

        for (MessageModel message : this.chat.getMessages()) {
            String sender = message.getSender();
            String receiver = message.getReceiver();

            if (!contacts.contains(sender)) {
                contacts.add(sender);
            }

            if (!contacts.contains(receiver)) {
                contacts.add(receiver);
            }
        }

        return contacts;
    }

    public List<MessageModel> getLastMessageOfConversation() {
        List<MessageModel> messages = new ArrayList<>();

        for (String contact : getConversationContacts()) {
            List<MessageModel> messagesOfConversation = getMessagesOfConversation(contact);

            if (messagesOfConversation.size() > 0) {
                messages.add(messagesOfConversation.get(messagesOfConversation.size() - 1));
            }
        }

        Collections.sort(messages);
        return messages;
    }

    public List<MessageModel> getSentMessages() {
        List<MessageModel> sentMessages = new ArrayList<>();
        String idMachine = AppDataService.getIdMachine();

        for (MessageModel message : this.chat.getMessages()) {
            if (message.isBy(idMachine)) {
                sentMessages.add(message);
            }
        }

        Collections.sort(sentMessages, Collections.reverseOrder());
        return sentMessages;
    }

    public List<MessageModel> getReceivedMessages() {
        List<MessageModel> receivedMessages = new ArrayList<>();
        String idMachine = AppDataService.getIdMachine();

        for (MessageModel message : this.chat.getMessages()) {
            if (message.isFor(idMachine)) {
                receivedMessages.add(message);
            }
        }

        Collections.sort(receivedMessages, Collections.reverseOrder());
        return receivedMessages;
    }

    public boolean removeConversation(ContactModel contact) {
        List<MessageModel> conversations = this.getMessagesOfConversation(contact.getIdContact());

        for (MessageModel message : conversations) {
            if (!removeMessage(message)) {
                return false;
            }
        }

        return true;
    }
}
