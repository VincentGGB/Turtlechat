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

import com.slowchat.vault.service.VaultService;

public class VaultViewModel {
    private VaultModel model = new VaultModel();

    public boolean createVault(String key, byte[] file, String type){
        VaultService vaultService = new VaultService();
        VaultModel model = new VaultModel();
        model.setKey(key);
        model.setFile(file);
        model.setType(type);
        long output = vaultService.createVault(model.mapObjectToDbObject());
        return output != -1L;
    }

    public boolean deleteVault(VaultModel model){
        VaultService vaultService = new VaultService();
        return vaultService.deleteVault(model.mapObjectToDbObject());
    }

    public boolean replaceVault(String key, byte[] file, String type){
        VaultService vaultService = new VaultService();
        return vaultService.replaceVault(key, file, type);
    }

    public boolean downloadData(String key){
        VaultService vaultService = new VaultService();
        return false;
    }

    public VaultModel getVault(){
        return model;
    }
}
