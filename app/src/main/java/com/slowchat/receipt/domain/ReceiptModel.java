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
package com.slowchat.receipt.domain;

import java.io.Serializable;
import java.util.Date;

public class ReceiptModel implements Serializable {
    private String sender;
    private String receiver;
    private Date sentDate;
    private Date receptionDate;

    public ReceiptModel(String sender, String receiver, Date sentDate, Date receptionDate) {
        this.sender = sender;
        this.receiver = receiver;
        this.sentDate = sentDate;
        this.receptionDate = receptionDate;
    }

    public ReceiptModel(com.slowchat.receipt.service.ReceiptModel receipt) {
        this.sender = receipt.getIdSender();
        this.receiver = receipt.getIdReceiver();
        this.sentDate = receipt.getSentDate();
        this.receptionDate = receipt.getReceptionDate();
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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

    public com.slowchat.receipt.service.ReceiptModel mapObjectToDbObject() {
        return new com.slowchat.receipt.service.ReceiptModel(this.sender,this.receiver,this.sentDate,this.receptionDate);
    }
    @Override
    public String toString() {
        return "ReceiptModel{" +
                "sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", sentDate=" + sentDate +
                ", receptionDate=" + receptionDate +
                '}';
    }
}
