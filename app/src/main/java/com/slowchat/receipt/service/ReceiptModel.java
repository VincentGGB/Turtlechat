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

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "receipt")
public class ReceiptModel {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long idReceipt;
    private String idSender;
    private String idReceiver;
    private Date sentDate;
    private Date receptionDate;


    public ReceiptModel(String idSender, String idReceiver, Date sentDate, Date receptionDate) {
        this.idSender = idSender;
        this.idReceiver = idReceiver;
        this.sentDate = sentDate;
        this.receptionDate = receptionDate;
    }

    public long getIdReceipt() {
        return idReceipt;
    }

    public void setIdReceipt(long idReceipt) {
        this.idReceipt = idReceipt;
    }

    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public String getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(String idReceiver) {
        this.idReceiver = idReceiver;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public Date getReceptionDate() {
        return receptionDate;
    }

    public void setReceptionDate(Date receptionDate) {
        this.receptionDate = receptionDate;
    }
}
