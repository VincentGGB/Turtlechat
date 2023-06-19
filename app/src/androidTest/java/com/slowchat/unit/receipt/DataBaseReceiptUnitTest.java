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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.slowchat.receipt.service.ReceiptDAO;
import com.slowchat.receipt.service.ReceiptModel;
import com.slowchat.utilities.database.AppDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DataBaseReceiptUnitTest {

    private AppDatabase db;

    private ReceiptDAO dao;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();

        db = Room.databaseBuilder(context, AppDatabase.class, "test")
                .allowMainThreadQueries()
                .build();

        db.clearAllTables();
        dao = db.getReceiptDAO();
        dao.resetAutoIncrement();
    }


    @After
    public void closeDb() {
        initTest();
        db.close();
    }

    @Test
    public void databaseShouldBeOpened() {
        assertTrue(db.isOpen());
    }

    @Test
    public void shouldGetOneById() {
        initTest();

        //GIVEN
        ReceiptModel receipt = createTestRow(0);
        long[] id = dao.insert(receipt);
        receipt.setIdReceipt(id[0]);

        //WHEN
        ReceiptModel receiptDb = dao.getReceiptById(id[0]);

        //THEN
        assertEquals(id[0], receiptDb.getIdReceipt());
    }

    @Test
    public void shouldBeUpdated() {
        initTest();

        //GIVEN
        ReceiptModel receipt = createTestRow(0);
        long[] id = dao.insert(receipt);
        receipt.setIdReceipt(id[0]);

        String newIdReceiver = "EDIT";
        receipt.setIdReceiver(newIdReceiver);

        //WHEN
        int nbLineUpdated = dao.update(receipt);
        ReceiptModel receiptDb = dao.getReceiptById(id[0]);

        //THEN
        assertEquals(id[0], receiptDb.getIdReceipt());
        assertEquals(newIdReceiver, receiptDb.getIdReceiver());
        assertEquals(nbLineUpdated, 1);
    }

    @Test
    public void shouldBeDeleted() {
        initTest();

        //GIVEN
        ReceiptModel receipt = createTestRow(0);
        long[] id = dao.insert(receipt);
        receipt.setIdReceipt(id[0]);

        //WHEN
        int nbLineDeleted = dao.delete(receipt);
        ReceiptModel receiptDb = dao.getReceiptById(id[0]);

        //THEN
        assertNull(receiptDb);
        assertEquals(nbLineDeleted, 1);
    }

    @Test
    public void shouldGetList() {
        initTest();

        //GIVEN
        int nb_row = 10;
        List<ReceiptModel> receipts = insertXRows(nb_row);

        //WHEN
        List<ReceiptModel> contactsDb = dao.getReceipts();

        //THEN
        for (int i = 1; i < nb_row; i++) {
            assertEquals(receipts.get(i).getIdReceipt(), contactsDb.get(i).getIdReceipt());
        }
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


    private List<ReceiptModel> insertXRows(int x) {
        ReceiptModel receipt;
        List<ReceiptModel> receipts = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            receipt = createTestRow(i);
            long[] id = dao.insert(receipt);
            receipt.setIdReceipt(id[0]);
            receipts.add(receipt);
        }
        return receipts;
    }

    private void initTest() {
        db.clearAllTables();
        dao.resetAutoIncrement();
    }
}*/