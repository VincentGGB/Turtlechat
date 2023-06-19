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
/*package com.slowchat.unit.letterbox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.slowchat.letterbox.service.LetterBoxDAO;
import com.slowchat.letterbox.service.LetterBoxModel;
import com.slowchat.letterbox.service.StateType;
import com.slowchat.utilities.database.AppDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DataBaseLetterBoxUnitTest {

    private AppDatabase db;

    private LetterBoxDAO dao;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();

        db = Room.databaseBuilder(context, AppDatabase.class, "test")
                .allowMainThreadQueries()
                .build();

        db.clearAllTables();
        dao = db.getLetterBoxDAO();
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
        LetterBoxModel letterBox = createTestRow(0);
        long[] id = dao.insert(letterBox);
        letterBox.setIdLetterBox(id[0]);

        //WHEN
        LetterBoxModel letterBoxDb = dao.getLetterBoxById(id[0]);

        //THEN
        assertEquals(id[0], letterBoxDb.getIdLetterBox());
    }

    @Test
    public void shouldBeUpdated() {
        initTest();

        //GIVEN
        LetterBoxModel letterBox = createTestRow(0);
        long[] id = dao.insert(letterBox);
        letterBox.setIdLetterBox(id[0]);

        StateType newStateLetterBox = StateType.DISABLED;
        letterBox.setState(newStateLetterBox);

        //WHEN

        int nbLineUpdated = dao.update(letterBox);
        LetterBoxModel letterBoxDb = dao.getLetterBoxById(id[0]);

        //THEN
        assertEquals(id[0], letterBoxDb.getIdLetterBox());
        assertEquals(newStateLetterBox, letterBoxDb.getState());
        assertEquals(nbLineUpdated, 1);
    }

    @Test
    public void shouldBeDeleted() {
        initTest();

        //GIVEN
        LetterBoxModel letterBox = createTestRow(0);
        long[] id = dao.insert(letterBox);
        letterBox.setIdLetterBox(id[0]);

        //WHEN
        int nbLineDeleted = dao.delete(letterBox);

        LetterBoxModel letterBoxDb = dao.getLetterBoxById(id[0]);

        //THEN
        assertNull(letterBoxDb);
        assertEquals(nbLineDeleted, 1);

    }

    @Test
    public void shouldGetList() {
        initTest();

        //GIVEN
        int nb_row = 10;
        List<LetterBoxModel> letterBoxs = insertXRows(nb_row);

        //WHEN
        List<LetterBoxModel> contactsBDD = dao.getLetterBoxes();

        //THEN
        for (int i = 1; i < nb_row; i++) {
            assertEquals(letterBoxs.get(i).getIdLetterBox(), contactsBDD.get(i).getIdLetterBox());
        }
    }

    private LetterBoxModel createTestRow(int i) {
        LetterBoxModel letterBox = new LetterBoxModel();
        switch (i % 3) {
            case 0:
                letterBox.setState(StateType.ENABLED);
                break;
            case 1:
                letterBox.setState(StateType.DISABLED);
                break;
            case 2:
                letterBox.setState(StateType.DELETED);
                break;
        }
        letterBox.setLatitude((float) (i * 0.1));
        letterBox.setLongitude((float) (i * 0.1));
        Calendar calendar = Calendar.getInstance();
        calendar.set((int) (2000L + i), 1, 1, 0, 0, 0);
        letterBox.setLastUpdated(calendar.getTime());
        return letterBox;
    }


    private List<LetterBoxModel> insertXRows(int x) {
        LetterBoxModel letterBox;
        List<LetterBoxModel> letterBoxs = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            letterBox = createTestRow(i);
            long[] id = dao.insert(letterBox);
            letterBox.setIdLetterBox(id[0]);
            letterBoxs.add(letterBox);
        }
        return letterBoxs;
    }

    private void initTest() {
        db.clearAllTables();
        dao.resetAutoIncrement();
    }
}
*/