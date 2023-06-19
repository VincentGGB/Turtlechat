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
/*package com.slowchat.unit.receipt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.slowchat.receipt.service.ReceiptModel;
import com.slowchat.receipt.service.ReceiptService;
import com.slowchat.utilities.database.AppDatabase;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReceiptServiceUnitTest {

    private AppDatabase db;
    private ReceiptService service;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();

        AppDatabase.setApp²Database(context);
        db = AppDatabase.getAppDatabase();
        initTest();

        service = new ReceiptService();
    }

    @Test
    public void shouldReturnTrueWhenInserting() {
        initTest();

        //GIVEN
        ReceiptModel receipt = createTestRow(0);

        //WHEN
        long ouput = service.insertReceipt(receipt);
        List<ReceiptModel> receiptModels = service.getReceipts();

        //THEN
        assertNotEquals(ouput, -1L);
        assertEquals(receiptModels.size(),1);
    }

    @Test
    public void shouldReturnTrueWhenDeleting() {
        initTest();

        //GIVEN
        ReceiptModel receipt = createTestRow(0);
        long id = service.insertReceipt(receipt);
        receipt.setIdReceipt(id);

        //WHEN
        boolean ouput = service.deleteReceipt(receipt);
        List<ReceiptModel> receiptModels = service.getReceipts();

        //THEN
        assertTrue(ouput);
        assertEquals(receiptModels.size(),0);
    }

    @Test
    public void shouldNotReturnTrueWhenDeleting() {
        initTest();

        //GIVEN
        ReceiptModel receipt = createTestRow(0);

        //WHEN
        boolean ouput = service.deleteReceipt(receipt);

        //THEN
        assertFalse(ouput);
    }

    @Test
    public void shouldDeleteOnlyOldReceipts() {
        initTest();
        //GIVEN
        for (int i = 0; i < 23; i++) {
            ReceiptModel receipt = createTestRow(i);
            service.insertReceipt(receipt);
        }
        List<ReceiptModel> receipts = service.getReceipts();

        //WHEN
        int ouput = service.deleteOldReceipts();
        List<ReceiptModel> receiptModels = service.getReceipts();

        //THEN
        assertEquals(ouput,14);
        assertEquals(receiptModels.size(),9);
    }

    private void initTest() {
        db.clearAllTables();
    }

    private ReceiptModel createTestRow(long i) {
        ReceiptModel receipt = new ReceiptModel();
        receipt.setIdReceiver("MAC" + i % 3);
        receipt.setIdSender("MAC" + (i + 1) % 3);
        receipt.setReceptionDate(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.set((int) (2000L + i), 1, 1, 0, 0, 0);
        receipt.setReceptionDate(calendar.getTime());
        return receipt;
    }
}*/
