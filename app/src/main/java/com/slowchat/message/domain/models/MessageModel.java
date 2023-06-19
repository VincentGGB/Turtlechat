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
package com.slowchat.message.domain.models;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class MessageModel implements Comparable<MessageModel>, Serializable {
    private String message;
    private String sender;
    private String receiver;
    private Date sentDate;
    private Date receptionDate;

    public MessageModel(com.slowchat.message.service.MessageModel message) {
        this.message = message.getMessage();
        this.sender = message.getSender();
        this.receiver = message.getReceiver();
        this.sentDate = message.getSentDate();
        this.receptionDate = message.getReceptionDate();
    }

    public MessageModel(String message, String sender, String receiver, Date sentDate, Date receptionDate) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.sentDate = sentDate;
        this.receptionDate = receptionDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public com.slowchat.message.service.MessageModel mapObjectToDbObject() {
        com.slowchat.message.service.MessageModel messageModel = new com.slowchat.message.service.MessageModel();

        messageModel.setMessage(this.message);
        messageModel.setSender(this.sender);
        messageModel.setReceiver(this.receiver);
        messageModel.setSentDate(this.sentDate);
        messageModel.setReceptionDate(this.receptionDate);

        return messageModel;
    }

    public boolean isExpired() {
        long compareDate = new Date().getTime() - this.sentDate.getTime();
        long tenYears = 315569520000L;

        return compareDate > tenYears;
    }

    public boolean isFor(String id) {
        return this.receiver.equals(id);
    }

    public boolean isBy(String id) {
        return this.sender.equals(id);
    }

    public boolean isReceived() {
        return this.receptionDate != null;
    }

    @Override
    public int compareTo(MessageModel message) {
        if (this.sentDate == null || message.sentDate == null) {
            return -1;
        }

        return this.sentDate.compareTo(message.sentDate);
    }

    @NonNull
    @Override
    public String toString() {
        return "MessageModel{" +
                "message='" + message + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", sentDate=" + sentDate +
                ", receptionDate=" + receptionDate +
                '}';
    }
}
