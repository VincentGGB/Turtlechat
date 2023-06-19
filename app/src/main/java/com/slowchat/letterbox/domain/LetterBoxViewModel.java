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
package com.slowchat.letterbox.domain;

import static com.slowchat.letterbox.domain.LetterBoxModel.mapDbListObjectsToDomainListObjects;
import static com.slowchat.letterbox.domain.LetterBoxModel.mapDbObjectToDomainObject;

import com.slowchat.letterbox.service.LetterBoxService;

import com.slowchat.appdata.service.AppDataService;

import java.io.IOException;
import java.util.List;

public class LetterBoxViewModel {

    public static void editLetterBox(com.slowchat.letterbox.domain.LetterBoxModel letterBox) {
        LetterBoxService.setLetterBoxService(letterBox.mapObjectToDbObject());
    }

    public static boolean addLetterBox(com.slowchat.letterbox.domain.LetterBoxModel letterBox) {
        return LetterBoxService.insertLetterBoxService(letterBox.mapObjectToDbObject());
    }

    private void deleteLetterBox(com.slowchat.letterbox.domain.LetterBoxModel letterBox) {
        LetterBoxService.deleteLetterBoxService(letterBox.mapObjectToDbObject());
    }

    public static List<LetterBoxModel> getLetterBoxes() {
        return mapDbListObjectsToDomainListObjects(LetterBoxService.getLetterBoxesService());
    }

    public static com.slowchat.letterbox.domain.LetterBoxModel getLetterBox(String id) {
        return mapDbObjectToDomainObject(LetterBoxService.getLetterBoxService(id));
    }

    public static void swapToLetterBox() {
        AppDataService.setLetterBoxMode(true);
    }

    public static void swapToUser() {
        AppDataService.setLetterBoxMode(false);
    }

    public static boolean isALetterBox() {
        return AppDataService.isLetterBox();
    }

}
