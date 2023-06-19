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
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.slowchat.appdata.service.AppDataService;
import com.slowchat.contact.service.ContactDAO;
import com.slowchat.contact.service.ContactModel;
import com.slowchat.message.domain.ChatModel;
import com.slowchat.message.domain.ChatViewModel;
import com.slowchat.message.service.MessageDAO;
import com.slowchat.message.service.MessageModel;
import com.slowchat.utilities.database.AppDatabase;
import com.slowchat.utilities.sharedpreferences.SharedPreferences;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChatViewModelUnitTest {
    private AppDatabase db;
    private ContactDAO contactDAO;
    private MessageDAO messageDao;
    private ChatViewModel chatViewModel;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();

        AppDatabase.setAppDatabase(context);
        SharedPreferences.setSharedPreferences(context);

        db = AppDatabase.getAppDatabase();
        contactDAO = db.getContactDAO();
        messageDao = db.getMessageDAO();

        chatViewModel = new ChatViewModel();
        AppDataService.setDefaultData("IDUNIQUE");
    }

    @Test
    public void shouldInitEmptyDataFromDB() {
        //GIVEN
        initTest();

        //WHEN
        boolean output = chatViewModel.getAllInformationFromDb();
        ChatModel chatModel = chatViewModel.getChat();

        //THEN
        assertEquals(chatModel.getMessages().size(), 0);
        assertEquals(chatModel.getContacts().size(), 0);
        assertTrue(output);
    }

    @Test
    public void shouldInitDataFromDB() {
        //GIVEN
        initTest();
        for (int i = 0; i < 5; i++) {
            contactDAO.insert(createTestRowContact(i));
            messageDao.insert(createTestRowMessage(i));
        }


        //WHEN
        boolean output = chatViewModel.getAllInformationFromDb();
        ChatModel chatModel = chatViewModel.getChat();

        //THEN
        assertEquals(chatModel.getMessages().size(), 5);
        assertEquals(chatModel.getContacts().size(), 5);
        assertTrue(output);
    }

    @Test
    public void shouldSendOneMessage() {
        //GIVEN
        initTest();
        for (int i = 0; i < 5; i++) {
            contactDAO.insert(createTestRowContact(i));
            messageDao.insert(createTestRowMessage(i));
        }
        chatViewModel.getAllInformationFromDb();

        //WHEN
        boolean output = chatViewModel.sendMessage(new com.slowchat.message.domain.models.MessageModel(createTestRowMessage(6)));
        ChatModel chatModel = chatViewModel.getChat();

        //THEN
        assertEquals(chatModel.getMessages().size(), 6);
        assertEquals(messageDao.getMessages().size(), 6);
        assertTrue(output);
    }

    @Test
    public void shouldDeleteOneMessage() {
        //GIVEN
        initTest();
        for (int i = 0; i < 5; i++) {
            contactDAO.insert(createTestRowContact(i));
            messageDao.insert(createTestRowMessage(i));
        }
        chatViewModel.getAllInformationFromDb();

        //WHEN
        ChatModel chatModel = chatViewModel.getChat();
        boolean output = chatViewModel.removeMessage(chatModel.getMessages().get(0));

        //THEN
        assertEquals(chatModel.getMessages().size(), 4);
        assertEquals(messageDao.getMessages().size(), 4);
        assertTrue(output);
    }

    @Test
    public void shouldGetAllMessageSentForMe() {
        //GIVEN
        initTest();

        for (int i = 0; i < 6; i++) {
            contactDAO.insert(createTestRowContact(i));
            messageDao.insert(createTestRowMessage(i));
        }
        chatViewModel.getAllInformationFromDb();

        //WHEN
        List<com.slowchat.message.domain.models.MessageModel> messages = chatViewModel.getReceivedMessages();

        //THEN
        assertEquals(messages.size(), 2);
    }

    @Test
    public void shouldGetAllMessageSentByMe() {
        //GIVEN
        initTest();

        for (int i = 0; i < 6; i++) {
            contactDAO.insert(createTestRowContact(i));
            messageDao.insert(createTestRowMessage(i));
        }
        chatViewModel.getAllInformationFromDb();

        //WHEN
        List<com.slowchat.message.domain.models.MessageModel> messages = chatViewModel.getSentMessages();

        //THEN
        assertEquals(messages.size(), 2);
    }

    @Test
    public void shouldGetAllMessageOfConversation() {
        //GIVEN
        initTest();

        for (int i = 0; i < 6; i++) {
            contactDAO.insert(createTestRowContact(i));
            messageDao.insert(createTestRowMessage(i));
        }
        MessageModel messageModel = new MessageModel();
        messageModel.setReceiver("MAC1");
        messageModel.setSender("MAC0");
        messageModel.setReceptionDate(new Date());
        messageDao.insert(messageModel);

        chatViewModel.getAllInformationFromDb();
        com.slowchat.message.domain.models.ContactModel contactModel = new com.slowchat.message.domain.models.ContactModel("MAC1", "");

        //WHEN
        List<com.slowchat.message.domain.models.MessageModel> messages = chatViewModel.getMessagesOfConversation(contactModel.getIdContact());

        //THEN
        assertEquals(messages.size(), 3);
    }

    @Test
    public void shouldRemoveAllMessageOfConversation() {
        //GIVEN
        initTest();

        for (int i = 0; i < 6; i++) {
            contactDAO.insert(createTestRowContact(i));
            messageDao.insert(createTestRowMessage(i));
        }
        MessageModel messageModel = new MessageModel();
        messageModel.setReceiver("MAC1");
        messageModel.setSender("MAC0");
        messageDao.insert(messageModel);

        chatViewModel.getAllInformationFromDb();
        com.slowchat.message.domain.models.ContactModel contactModel = new com.slowchat.message.domain.models.ContactModel("MAC1", "");

        //WHEN
        boolean output = chatViewModel.removeConversation(contactModel);

        //THEN
        List<MessageModel> messageModels = messageDao.getMessages();
        assertTrue(output);
        assertEquals(messageModels.size(), 4);
        assertEquals(chatViewModel.getChat().getMessages().size(), 4);

        for (MessageModel message : messageModels) {
            com.slowchat.message.domain.models.MessageModel model = new com.slowchat.message.domain.models.MessageModel(message);
            assertFalse(model.isFor("MAC0") && model.isBy(contactModel.getIdContact()) ||
                    (model.isBy("MAC0") && model.isFor(contactModel.getIdContact())));
        }

        for (com.slowchat.message.domain.models.MessageModel model : chatViewModel.getChat().getMessages()) {
            assertFalse(model.isFor("MAC0") && model.isBy(contactModel.getIdContact()) ||
                    (model.isBy("MAC0") && model.isFor(contactModel.getIdContact())));
        }
    }

    @Test
    public void shouldGetAllContacts() {
        //GIVEN
        initTest();

        for (int i = 0; i < 6; i++) {
            messageDao.insert(createTestRowMessage(i));
        }

        chatViewModel.getAllInformationFromDb();

        //WHEN
        List<String> conversationContacts = chatViewModel.getConversationContacts();

        //THEN
        assertEquals(3, conversationContacts.size());
    }

    @Test
    public void shouldGetLastMessageOfAConversation() {
        //GIVEN
        initTest();

        for (int i = 0; i < 6; i++) {
            messageDao.insert(createTestRowMessage(i));
        }

        chatViewModel.getAllInformationFromDb();

        //WHEN
        List<com.slowchat.message.domain.models.MessageModel> lastMessageOfConversation = chatViewModel.getLastMessageOfConversation();

        //THEN
        assertEquals(2, lastMessageOfConversation.size());
        assertEquals("MAC0", lastMessageOfConversation.get(0).getReceiver());
        assertEquals("MAC1", lastMessageOfConversation.get(0).getSender());
        assertEquals("MAC0", lastMessageOfConversation.get(1).getSender());
        assertEquals("MAC2", lastMessageOfConversation.get(1).getReceiver());
    }

    private void initTest() {
        db.clearAllTables();
        messageDao.resetAutoIncrement();
    }


    private MessageModel createTestRowMessage(int i) {
        MessageModel message = new MessageModel();
        message.setReceiver("MAC" + i % 3);
        message.setSender("MAC" + (i + 1) % 3);

        Calendar calendar = Calendar.getInstance();
        calendar.set((int) (2000L + i), 1, 1, 0, 0, 0);
        message.setSentDate(calendar.getTime());
        calendar.set((int) (2023L - i), 2, 1, 0, 0, 0);
        message.setReceptionDate(calendar.getTime());
        message.setMessage("Message" + i);
        return message;
    }

    private ContactModel createTestRowContact(int i) {
        ContactModel contact = new ContactModel();
        contact.setIdContact("MAC" + i);
        contact.setUsername("username" + i);
        return contact;
    }
}
