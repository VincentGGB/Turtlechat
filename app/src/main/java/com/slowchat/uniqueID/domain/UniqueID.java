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
package com.slowchat.uniqueID.domain;

import com.slowchat.appdata.service.AppDataService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class UniqueID {
    private static String generateRandomKey() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            int index = random.nextInt(characters.length());
            stringBuilder.append(characters.charAt(index));
        }
        return stringBuilder.toString();
    }

    public static void createUniqueID(String passwordToEncrypt) throws NoSuchAlgorithmException {
        String salt = generateRandomKey();
        AppDataService.setSalt(salt);

        String saltedPassword = passwordToEncrypt + salt;
        String hashedPassword = hashPassword(saltedPassword);
        AppDataService.setIdMachine(hashedPassword);
    }

    public static String hashPassword(String saltedPassword) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedPassword = md.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : hashedPassword) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static void reuseOldUniqueID(String oldPassword, String salt) throws NoSuchAlgorithmException {
        String saltedPassword = oldPassword + salt;
        String hashedPassword = hashPassword(saltedPassword);
        AppDataService.setIdMachine(hashedPassword);
    }
}
