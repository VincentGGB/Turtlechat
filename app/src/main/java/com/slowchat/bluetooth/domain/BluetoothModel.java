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
package com.slowchat.bluetooth.domain;

import androidx.annotation.NonNull;

import com.slowchat.letterbox.domain.LetterBoxModel;
import com.slowchat.message.domain.models.MessageModel;
import com.slowchat.receipt.domain.ReceiptModel;

import java.io.Serializable;
import java.util.List;

public class BluetoothModel implements Serializable {
    private final List<MessageModel> messages;
    private final List<LetterBoxModel> letterBoxes;
    private final List<ReceiptModel> receipts;

    public BluetoothModel(List<MessageModel> messages, List<LetterBoxModel> letterBoxes, List<ReceiptModel> receipts) {
        this.messages = messages;
        this.letterBoxes = letterBoxes;
        this.receipts = receipts;
    }

    public List<MessageModel> getMessages() {
        return messages;
    }

    public List<LetterBoxModel> getLetterBoxes() {
        return letterBoxes;
    }

    public List<ReceiptModel> getReceipts() {
        return receipts;
    }

    @NonNull
    @Override
    public String toString() {
        return "BluetoothModel{" +
                "messages=" + messages +
                ", letterBoxes=" + letterBoxes +
                ", receipts=" + receipts +
                '}';
    }
}
