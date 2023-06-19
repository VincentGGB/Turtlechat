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
package com.slowchat.utilities.error;

import android.content.Context;
import android.widget.Toast;

import com.slowchat.utilities.InterfaceErrorMessages.EventType;

public class ToastError {
    private final Context context;

    public ToastError(Context context) {
        this.context = context;
    }

    public Toast createToast(EventType eventType) {
        return Toast.makeText(context, eventType.getEventLabel(), Toast.LENGTH_SHORT);
    }
}
