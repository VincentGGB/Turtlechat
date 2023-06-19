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
package com.slowchat.message.service;

import com.slowchat.utilities.database.AppDatabase;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MessageService {
    private final static MessageDAO dao = AppDatabase.getAppDatabase().getMessageDAO();

    public static long insertMessage(MessageModel message) {
        return dao.insert(message)[0];
    }

    public static void insertMessages(List<MessageModel> messages) {
        for (MessageModel message : messages) {
            dao.insert(message);
        }
    }

    public static MessageModel getMessage(MessageModel message) {
        return dao.getMessage(message.getSender(), message.getReceiver(), message.getSentDate());
    }

    public static List<MessageModel> getMessages() {
        return dao.getMessages();
    }

    public static boolean updateMessage(MessageModel model) {
        return dao.update(model) == 1;
    }

    public static boolean deleteMessage(MessageModel model) {
        return dao.delete(model) == 1;
    }

    public static int deleteOldMessages() {
        return dao.deleteOldMessages(new Date());
    }

    public static boolean alreadyExists(MessageModel message) {
        return dao.alreadyExists(message.getMessage(), message.getSender(), message.getReceiver(), message.getSentDate()) > 0;
    }
}
