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
package com.slowchat.appdata.service;

import com.slowchat.utilities.sharedpreferences.SharedPreferences;

public class AppDataService {
    static SharedPreferences sharedPreferences = new SharedPreferences();

    private enum EnumData {
        ID_MACHINE, THEME, IS_LETTERBOX, MEMORY_SIZE_CARTO, MEMORY_SIZE_MESSAGE, MEMORY_SIZE_MAP, SALT;

        public String getEventLabel() {
            switch (this) {
                case ID_MACHINE:
                    return "id_machine";
                case THEME:
                    return "theme";
                case IS_LETTERBOX:
                    return "is_letter_box";
                case MEMORY_SIZE_CARTO:
                    return "memory_size_carto";
                case MEMORY_SIZE_MESSAGE:
                    return "memory_size_message";
                case MEMORY_SIZE_MAP:
                    return "memory_size_map";
                case SALT:
                    return "salt";
                default:
                    return null;
            }
        }
    }

    static public void initAppDataService(String mac) {
        String idMachine = (String) sharedPreferences.getData(EnumData.ID_MACHINE.getEventLabel(), SharedPreferences.EnumType.STRING);

        if (idMachine == null) {
            setDefaultData(mac);
        }
    }

    static public void setIdMachine(String idMachine) {
        sharedPreferences.setData(EnumData.ID_MACHINE.getEventLabel(), idMachine);
    }

    static public String getIdMachine() {
        return (String) sharedPreferences.getData(EnumData.ID_MACHINE.getEventLabel(), SharedPreferences.EnumType.STRING);
    }

    static public void setTheme(String theme) {
        sharedPreferences.setData(EnumData.THEME.getEventLabel(), theme);
    }

    static public String getTheme() {
        return (String) sharedPreferences.getData(EnumData.THEME.getEventLabel(), SharedPreferences.EnumType.STRING);
    }

    static public void setLetterBoxMode(boolean isLetterBox) {
        sharedPreferences.setData(EnumData.IS_LETTERBOX.getEventLabel(), isLetterBox);
    }

    static public boolean isLetterBox() {
        return (boolean) sharedPreferences.getData(EnumData.IS_LETTERBOX.getEventLabel(), SharedPreferences.EnumType.BOOLEAN);
    }

    static public void setMemorySizeCarto(int memorySizeCarto) {
        sharedPreferences.setData(EnumData.MEMORY_SIZE_CARTO.getEventLabel(), memorySizeCarto);
    }

    static public int getMemorySizeCarto() {
        return (int) sharedPreferences.getData(EnumData.MEMORY_SIZE_CARTO.getEventLabel(), SharedPreferences.EnumType.INT);
    }

    static public void setMemorySizeMessage(int memorySizeMessage) {
        sharedPreferences.setData(EnumData.MEMORY_SIZE_MESSAGE.getEventLabel(), memorySizeMessage);
    }

    static public int getMemorySizeMessage() {
        return (int) sharedPreferences.getData(EnumData.MEMORY_SIZE_MESSAGE.getEventLabel(), SharedPreferences.EnumType.INT);
    }

    static public void setMemorySizeMap(int memorySizeMap) {
        sharedPreferences.setData(EnumData.MEMORY_SIZE_MAP.getEventLabel(), memorySizeMap);
    }

    static public int getMemorySizeMap() {
        return (int) sharedPreferences.getData(EnumData.MEMORY_SIZE_MAP.getEventLabel(), SharedPreferences.EnumType.INT);
    }

    static public void setSalt(String salt) {
        sharedPreferences.setData(EnumData.SALT.getEventLabel(), salt);
    }

    static public String getSalt() {
        return (String) sharedPreferences.getData(EnumData.SALT.getEventLabel(), SharedPreferences.EnumType.STRING);
    }


    static public void setDefaultData(String mac) {
        sharedPreferences.setData(EnumData.ID_MACHINE.getEventLabel(), mac);
        sharedPreferences.setData(EnumData.THEME.getEventLabel(), "default");
        sharedPreferences.setData(EnumData.IS_LETTERBOX.getEventLabel(), false);
        sharedPreferences.setData(EnumData.MEMORY_SIZE_CARTO.getEventLabel(), 512);
        sharedPreferences.setData(EnumData.MEMORY_SIZE_MESSAGE.getEventLabel(), 512);
        sharedPreferences.setData(EnumData.MEMORY_SIZE_MAP.getEventLabel(), 512);
    }
}


