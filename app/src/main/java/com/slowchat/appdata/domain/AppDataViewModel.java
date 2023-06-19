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
package com.slowchat.appdata.domain;

import com.slowchat.appdata.service.AppDataService;

public class AppDataViewModel {

    static private AppDataModel appDataModel = new AppDataModel(AppDataService.getIdMachine(), AppDataService.getTheme(),
            AppDataService.isLetterBox(), AppDataService.getMemorySizeCarto(),
            AppDataService.getMemorySizeMessage(), AppDataService.getMemorySizeMap(), AppDataService.getSalt());

    static public void refreshViewModelData() {
        appDataModel = new AppDataModel(AppDataService.getIdMachine(), AppDataService.getTheme(),
                AppDataService.isLetterBox(), AppDataService.getMemorySizeCarto(),
                AppDataService.getMemorySizeMessage(), AppDataService.getMemorySizeMap(), AppDataService.getSalt());
    }

    static public AppDataModel getAppDataModel() {
        refreshViewModelData();
        return appDataModel;
    }

    static public void setTheme(String theme) {
        AppDataService.setTheme(theme);
        appDataModel.setTheme(theme);
    }

    static public void setLetterBoxMode(boolean isLetterBox) {
        AppDataService.setLetterBoxMode(isLetterBox);
        appDataModel.setLetterBoxMode(isLetterBox);
    }

    static public void setMemorySizeCarto(int memorySizeCarto) {
        AppDataService.setMemorySizeCarto(memorySizeCarto);
        appDataModel.setMemorySizeCarto(memorySizeCarto);
    }

    static public void setMemorySizeMessage(int memorySizeMessage) {
        AppDataService.setMemorySizeMessage(memorySizeMessage);
        appDataModel.setMemorySizeMessage(memorySizeMessage);
    }

    static public void setMemorySizeMap(int memorySizeMap) {
        AppDataService.setMemorySizeMap(memorySizeMap);
        appDataModel.setMemorySizeMap(memorySizeMap);
    }

    static public void setSalt(String salt) {
        AppDataService.setSalt(salt);
        appDataModel.setSalt(salt);
    }
}
