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
package com.slowchat.receipt.service;

import android.database.sqlite.SQLiteConstraintException;

import com.slowchat.utilities.database.AppDatabase;

import java.util.Date;
import java.util.List;

public class ReceiptService {
    private static final ReceiptDAO dao = AppDatabase.getAppDatabase().getReceiptDAO();

    public static long insertReceipt(ReceiptModel model) {
        long output;
        try {
            output = dao.insert(model)[0];
        } catch (SQLiteConstraintException e) {
            output = -1; // TODO : ADD ERROR MESSAGE
        }
        return output;
    }

    public static void insertReceipts(List<ReceiptModel> receipts) {
        for (ReceiptModel receipt : receipts) {
            dao.insert(receipt);
        }
    }

    public static ReceiptModel getReceipt(long id) {
        return dao.getReceiptById(id);
    }

    public static List<ReceiptModel> getReceipts() {
        return dao.getReceipts();
    }

    public static boolean updateReceipt(ReceiptModel model) {
        return dao.update(model) == 1;
    }

    public static boolean deleteReceipt(ReceiptModel model) {
        return dao.delete(model) == 1;
    }

    public static int deleteOldReceipts() {
        return dao.deleteOldReceipts(new Date());
    }
}
