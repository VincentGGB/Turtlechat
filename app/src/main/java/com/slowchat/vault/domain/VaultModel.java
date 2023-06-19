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
package com.slowchat.vault.domain;

public class VaultModel {
    private String key;
    private byte[] file;
    private String type;

    public VaultModel() {
    }

    public VaultModel(String key, byte[] file, String type) {
        this.key = key;
        this.file = file;
        this.type = type;
    }

    public com.slowchat.vault.service.VaultModel mapObjectToDbObject() {
        com.slowchat.vault.service.VaultModel model = new com.slowchat.vault.service.VaultModel();
        model.setKey(this.key);
        model.setFile(this.file);
        model.setType(this.type);
        return model;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
