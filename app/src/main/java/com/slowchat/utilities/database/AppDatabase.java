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
package com.slowchat.utilities.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.slowchat.vault.service.VaultDAO;
import com.slowchat.vault.service.VaultModel;
import com.slowchat.contact.service.ContactDAO;
import com.slowchat.contact.service.ContactModel;
import com.slowchat.letterbox.service.LetterBoxDAO;
import com.slowchat.letterbox.service.LetterBoxModel;
import com.slowchat.message.service.MessageDAO;
import com.slowchat.message.service.MessageModel;
import com.slowchat.receipt.service.ReceiptDAO;
import com.slowchat.receipt.service.ReceiptModel;
import com.slowchat.utilities.database.converters.DateConverter;

@Database(entities = {ContactModel.class, LetterBoxModel.class, MessageModel.class, ReceiptModel.class, VaultModel.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase;

    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }

    public static void setAppDatabase(Context context) {
        if (appDatabase == null || !appDatabase.isOpen())
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "slowchat")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
    }

    public abstract ContactDAO getContactDAO();

    public abstract LetterBoxDAO getLetterBoxDAO();

    public abstract MessageDAO getMessageDAO();

    public abstract ReceiptDAO getReceiptDAO();

    public abstract VaultDAO getVaultDAO();
}
