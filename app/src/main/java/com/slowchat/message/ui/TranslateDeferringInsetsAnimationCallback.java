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
package com.slowchat.message.ui;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.WindowInsetsAnimationCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class TranslateDeferringInsetsAnimationCallback extends WindowInsetsAnimationCompat.Callback {
    private final View view;

    public TranslateDeferringInsetsAnimationCallback(View view) {
        super(DISPATCH_MODE_STOP);
        this.view = view;
    }

    @NonNull
    @Override
    public WindowInsetsCompat onProgress(@NonNull WindowInsetsCompat insets, @NonNull List<WindowInsetsAnimationCompat> runningAnimations) {
        Insets diff = Insets.max(Insets.subtract(insets.getInsets(WindowInsetsCompat.Type.ime()),
                insets.getInsets(WindowInsetsCompat.Type.systemBars())), Insets.NONE);

        view.setTranslationY(diff.top - diff.bottom);
        return insets;
    }
}
