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

import androidx.room.Dao;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

@Dao
public interface MessageDAO extends com.slowchat.utilities.database.Dao<MessageModel> {
    @Query("SELECT * FROM message ORDER BY sentDate DESC")
    List<MessageModel> getMessages();

    @Query("DELETE FROM sqlite_sequence WHERE name='message'")
    void resetAutoIncrement();

    @Query("DELETE FROM message WHERE sentDate < :date-315569520000")
    int deleteOldMessages(Date date);

    @Query("SELECT count(*) FROM message WHERE message=:message AND sender=:sender AND receiver=:receiver AND sentDate=:sentDate")
    int alreadyExists(String message, String sender, String receiver, Date sentDate);

    @Query("SELECT * FROM message WHERE sender=:sender AND receiver=:receiver AND sentDate=:sentDate")
    MessageModel getMessage(String sender, String receiver, Date sentDate);
}
