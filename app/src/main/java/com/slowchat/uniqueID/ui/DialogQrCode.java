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
package com.slowchat.uniqueID.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.slowchat.R;
import com.slowchat.appdata.domain.AppDataViewModel;

public class DialogQrCode extends MaterialAlertDialogBuilder {

    public DialogQrCode(@NonNull Context context) {
        super(context);
        View view = View.inflate(context, R.layout.dialog_qrcode, null);
        setTitle("Partagez votre identifiant");

        TextView uniqueid = view.findViewById(R.id.contact_share_qrcode_text_uniqueid);
        uniqueid.setText("Votre identifiant unique : " + AppDataViewModel.getAppDataModel().getIdMachine());

        createQrCode(view);

        setView(view);
    }

    private void createQrCode(View view) {
        String myString = AppDataViewModel.getAppDataModel().getIdMachine();
        int size = 500;

// Encode the string using QR_CODE format
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = writer.encode(myString, BarcodeFormat.QR_CODE, size, size);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }

// Create a bitmap from the bit matrix
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, bitMatrix.get(x,y) ? ContextCompat.getColor(view.getContext(),R.color.seed) : Color.WHITE);
            }
        }

// Display the bitmap in an ImageView
        ImageView imageView = view.findViewById(R.id.imageQrCode);
        imageView.setImageBitmap(bmp);
    }
}
