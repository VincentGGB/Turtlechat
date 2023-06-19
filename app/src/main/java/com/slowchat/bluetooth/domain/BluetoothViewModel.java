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

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;

import com.slowchat.appdata.domain.AppDataViewModel;
import com.slowchat.appdata.service.AppDataService;
import com.slowchat.bluetooth.service.BluetoothService;
import com.slowchat.letterbox.service.LetterBoxModel;
import com.slowchat.letterbox.service.LetterBoxService;
import com.slowchat.message.domain.models.MessageModel;
import com.slowchat.message.service.MessageService;
import com.slowchat.receipt.service.ReceiptModel;
import com.slowchat.receipt.service.ReceiptService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SuppressLint("MissingPermission")
public class BluetoothViewModel {
    private final BluetoothService bluetoothService;

    public BluetoothViewModel(BluetoothService bluetoothService) {
        this.bluetoothService = bluetoothService;
    }

    public boolean isBluetoothAllowed() {
        return bluetoothService.isBluetoothAllowed();
    }

    public boolean isBluetoothEnabled() {
        return bluetoothService.isBluetoothEnabled();
    }

    public boolean isBluetoothScanAllowed() {
        return bluetoothService.isBluetoothScanAllowed();
    }

    public List<BluetoothDevice> getPairedDevices() {
        List<BluetoothDevice> devices = new ArrayList<>();
        Set<BluetoothDevice> pairedDevices = bluetoothService.getPairedDevices();

        for (BluetoothDevice device : pairedDevices) {
            if (device.getBluetoothClass().getDeviceClass() == BluetoothClass.Device.PHONE_SMART) {
                devices.add(device);
            }
        }

        return devices;
    }

    public void startDiscovery() {
        bluetoothService.startDiscovery();
    }

    public void cancelDiscovery() {
        bluetoothService.cancelDiscovery();
    }

    public void insertMessages(List<MessageModel> messages) {
        List<com.slowchat.message.service.MessageModel> convertedMessages = new ArrayList<>();

        for (MessageModel message : messages) {
            if (message.isReceived() || MessageService.alreadyExists(message.mapObjectToDbObject())) {
                continue;
            }

            if (message.getReceiver().equals(AppDataViewModel.getAppDataModel().getIdMachine())) {
                message.setReceptionDate(new Date());
                ReceiptService.insertReceipt(new ReceiptModel(message.getReceiver(), message.getSender(), message.getSentDate(), new Date()));

                //TODO renvoyer le receipt
            }
            convertedMessages.add(message.mapObjectToDbObject());
        }

        MessageService.insertMessages(convertedMessages);
    }

    public void insertReceipts(List<com.slowchat.receipt.domain.ReceiptModel> receipts) {
        List<ReceiptModel> convertedReceipt = new ArrayList<>();
        for (com.slowchat.receipt.domain.ReceiptModel receipt : receipts) {
            if (receipt.getReceiver().equals(AppDataService.getIdMachine())) {
                com.slowchat.message.service.MessageModel messageModel = MessageService.getMessage(new MessageModel("", receipt.getReceiver(), receipt.getSender(), receipt.getSentDate(), null).mapObjectToDbObject());
                if (messageModel.getReceptionDate() != null) {
                    continue;
                }
                messageModel.setReceptionDate(receipt.getReceptionDate());
                MessageService.updateMessage(messageModel);
                continue;
            }
            convertedReceipt.add(receipt.mapObjectToDbObject());
        }
        ReceiptService.insertReceipts(convertedReceipt);
    }

    public void insertLetterBoxes(List<com.slowchat.letterbox.domain.LetterBoxModel> LetterBoxes) {
        List<LetterBoxModel> convertedLetterBoxes = new ArrayList<>();
        for (com.slowchat.letterbox.domain.LetterBoxModel letterBox : LetterBoxes) {
            if (LetterBoxService.existLetterBoxService(letterBox.mapObjectToDbObject())) {
                LetterBoxModel model = LetterBoxService.getLetterBoxService(letterBox.getIdLetterBox());
                if (model.getLastUpdated().before(letterBox.getLastUpdated())) {
                    LetterBoxService.setLetterBoxService(model);
                }
                continue;
            }
            convertedLetterBoxes.add(letterBox.mapObjectToDbObject());
        }
        LetterBoxService.insertLetterBoxesService(convertedLetterBoxes);
    }
}
