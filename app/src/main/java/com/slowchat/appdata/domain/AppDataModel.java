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

public class AppDataModel {
    private String idMachine;
    private String theme;
    private boolean isLetterBox;
    private int memorySizeCarto;
    private int memorySizeMessage;
    private int memorySizeMap;

    private String salt;

    public AppDataModel() {

    }

    public AppDataModel(String idMachine, String theme, boolean isLetterBox, int memorySizeCarto, int memorySizeMessage, int memorySizeMap, String salt) {
        this.idMachine = idMachine;
        this.theme = theme;
        this.isLetterBox = isLetterBox;
        this.memorySizeCarto = memorySizeCarto;
        this.memorySizeMessage = memorySizeMessage;
        this.memorySizeMap = memorySizeMap;
        this.salt = salt;
    }

    public String getIdMachine() {
        return idMachine;
    }

    public void setIdMachine(String idMachine) {
        this.idMachine = idMachine;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public boolean isLetterBox() {
        return isLetterBox;
    }

    public void setLetterBoxMode(boolean isLetterBox) {
        this.isLetterBox = isLetterBox;
    }

    public int getMemorySizeCarto() {
        return memorySizeCarto;
    }

    public void setMemorySizeCarto(int memorySizeCarto) {
        this.memorySizeCarto = memorySizeCarto;
    }

    public int getMemorySizeMessage() {
        return memorySizeMessage;
    }

    public void setMemorySizeMessage(int memorySizeMessage) {
        this.memorySizeMessage = memorySizeMessage;
    }

    public int getMemorySizeMap() {
        return memorySizeMap;
    }

    public void setMemorySizeMap(int memorySizeMap) {
        this.memorySizeMap = memorySizeMap;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
