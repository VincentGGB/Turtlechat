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
package com.slowchat.utilities.database;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

public interface Dao<T> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] insert(T... items);

    @Update
    int update(T... items);

    @Delete
    int delete(T item);
}
