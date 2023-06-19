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
package com.slowchat.utilities.sharedpreferences;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import java.util.Set;

public class SharedPreferences {

    static private android.content.SharedPreferences sharedPreferences;

    public enum EnumType {
        BOOLEAN, INT, LONG, FLOAT, STRING, STRING_SET;
    }

    public static void setSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("application_data", MODE_PRIVATE);

    }

    public void setData(String key, boolean data) {
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, data);
        editor.apply();
    }

    public void setData(String key, int data) {
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, data);
        editor.apply();
    }

    public void setData(String key, long data) {
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, data);
        editor.apply();
    }

    public void setData(String key, float data) {
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, data);
        editor.apply();
    }

    public void setData(String key, String data) {
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, data);
        editor.apply();
    }

    public void setData(String key, Set<String> data) {
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, data);
        editor.apply();
    }

    /**
     * Method to get a data from sharedPreferences
     *
     * @param key
     * @param type could be "boolean,int,long,float,string,set<string>
     * @return Object that need to be cast
     */
    public Object getData(String key, EnumType type) {
        switch (type) {
            case BOOLEAN:
                return sharedPreferences.getBoolean(key, false);
            case INT:
                return sharedPreferences.getInt(key, -1);
            case LONG:
                return sharedPreferences.getLong(key, -1);
            case FLOAT:
                return sharedPreferences.getFloat(key, -1);
            case STRING:
                return sharedPreferences.getString(key, null);
            case STRING_SET:
                return sharedPreferences.getStringSet(key, null);
            default:
                return null; // TODO : make an error message
        }
    }
}
