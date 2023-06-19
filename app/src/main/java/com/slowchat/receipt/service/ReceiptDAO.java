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

import androidx.room.Dao;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

@Dao
public interface ReceiptDAO extends com.slowchat.utilities.database.Dao<ReceiptModel> {
    @Query("SELECT * FROM receipt WHERE idReceipt=:id")
    ReceiptModel getReceiptById(long id);

    @Query("SELECT * FROM receipt")
    List<ReceiptModel> getReceipts();

    @Query("DELETE FROM sqlite_sequence WHERE name='receipt'")
    void resetAutoIncrement();

    @Query("DELETE FROM receipt WHERE receptionDate < :date-315569520000")
    int deleteOldReceipts(Date date);
}
