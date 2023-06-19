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
package com.slowchat.unit.vault;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.slowchat.vault.service.VaultDAO;
import com.slowchat.vault.service.VaultModel;
import com.slowchat.utilities.database.AppDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DataBaseVaultUnitTest {

    private AppDatabase db;
    private VaultDAO dao;


    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();

        db = Room.databaseBuilder(context, AppDatabase.class, "test")
                .allowMainThreadQueries()
                .build();

        db.clearAllTables();
        dao = db.getVaultDAO();
    }


    @After
    public void closeDb() {
        db.clearAllTables();
        db.close();
    }

    @Test
    public void databaseShouldBeOpened() {
        assertTrue(db.isOpen());
    }

    @Test
    public void shouldGetOneById() {
        //GIVEN
        db.clearAllTables();
        String idSeached = "MAC2";
        insertXRows(5);

        //WHEN
        VaultModel vaultDb = dao.getVaultById(idSeached);

        //THEN
        assertEquals(idSeached, vaultDb.getKey());
    }

    @Test
    public void shouldBeUpdated() {
        //GIVEN
        db.clearAllTables();
        int row = 0;
        VaultModel vault = createTestRow(row);
        dao.insert(vault);

        String newType = "EDIT";
        vault.setType(newType);

        //WHEN
        int nbLineUpdated = dao.update(vault);
        VaultModel vaultDb = dao.getVaultById("MAC" + row);

        //THEN
        assertEquals(vault.getKey(), vaultDb.getKey());
        assertEquals(newType, vaultDb.getType());
        assertEquals(nbLineUpdated, 1);
    }

    @Test
    public void shouldBeDeleted() {
        //GIVEN
        db.clearAllTables();
        int row = 0;
        VaultModel vault = createTestRow(row);
        dao.insert(vault);

        //WHEN
        int nbLineDeleted = dao.delete(vault);
        VaultModel vaultDb = dao.getVaultById("MAC" + row);

        //THEN
        assertNull(vaultDb);
        assertEquals(nbLineDeleted, 1);
    }

    @Test
    public void shouldGetList() {
        //GIVEN
        db.clearAllTables();
        int nb_row = 10;
        List<VaultModel> vaults = insertXRows(nb_row);

        //WHEN
        List<VaultModel> vaultsBDD = dao.getVaults();

        //THEN
        for (int i = 0; i < nb_row; i++) {
            assertEquals(vaults.get(i).getKey(), vaultsBDD.get(i).getKey());
        }
    }

    private VaultModel createTestRow(int i) {
        VaultModel vault = new VaultModel();
        vault.setKey("MAC" + i);
        vault.setType("type" + i);
        vault.setFile("test".getBytes(StandardCharsets.UTF_8));
        return vault;
    }


    private List<VaultModel> insertXRows(int x) {
        VaultModel vault;
        List<VaultModel> vaults = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            vault = createTestRow(i);
            vaults.add(vault);
            dao.insert(vault);
        }
        return vaults;
    }
}
