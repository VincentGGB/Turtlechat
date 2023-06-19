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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.slowchat.R;

public class FragmentChat extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        FragmentChatAdapter adapter = new FragmentChatAdapter(this);
        ViewPager2 viewPager = view.findViewById(R.id.fragment_chat_viewpager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = view.findViewById(R.id.fragment_chat_tablayout);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText(R.string.navigation_chat);
                    break;
                case 1:
                    tab.setText(R.string.navigation_received);
                    break;
                case 2:
                    tab.setText(R.string.navigation_sent);
                    break;
            }
        }).attach();
    }

    static class FragmentChatAdapter extends FragmentStateAdapter {
        public FragmentChatAdapter(Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new FragmentChatMain();
                case 1:
                    return new FragmentChatReceived();
                case 2:
                    return new FragmentChatSent();
            }

            throw new RuntimeException();
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}
