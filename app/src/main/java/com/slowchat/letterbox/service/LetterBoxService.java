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
package com.slowchat.letterbox.service;

import android.database.sqlite.SQLiteConstraintException;

import com.slowchat.utilities.database.AppDatabase;

import java.util.List;

public class LetterBoxService {
    private static final AppDatabase appDatabase= AppDatabase.getAppDatabase();
    private static LetterBoxDAO letterBoxDAO = appDatabase.getLetterBoxDAO();

    public static boolean insertLetterBoxService(LetterBoxModel letterBox) {
        try {
            letterBoxDAO.insert(letterBox);
            return true;
        } catch (SQLiteConstraintException e) {
            return false;
        }
    }

    public static void insertLetterBoxesService(List<LetterBoxModel> letterBoxes) {
       for(LetterBoxModel model : letterBoxes){
           insertLetterBoxService(model);
       }
    }

    public static List<LetterBoxModel> getLetterBoxesService() {
        return letterBoxDAO.getLetterBoxes();
    }

    public static LetterBoxModel getLetterBoxService(String id) {
        return letterBoxDAO.getLetterBoxById(id);
    }

    public static void setLetterBoxService(LetterBoxModel letterBox) {
        letterBoxDAO.update(letterBox);
    }

    public static void deleteLetterBoxService(LetterBoxModel letterBox) {
        letterBoxDAO.delete(letterBox);
    }

    public static boolean existLetterBoxService(LetterBoxModel letterBox) {
        return letterBoxDAO.getLetterBoxById(letterBox.getIdLetterBox()) != null;
    }
}
