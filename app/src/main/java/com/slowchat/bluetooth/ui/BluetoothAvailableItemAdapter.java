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
package com.slowchat.bluetooth.ui;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.slowchat.R;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("MissingPermission")
public class BluetoothAvailableItemAdapter extends RecyclerView.Adapter<BluetoothAvailableItemAdapter.ViewHolder> {
    private final List<BluetoothDevice> devices;

    public BluetoothAvailableItemAdapter() {
        this.devices = new ArrayList<>();
    }

    public void addDevice(BluetoothDevice device) {
        if (device.getName() == null || this.devices.contains(device)) {
            return;
        }

        this.devices.add(device);
        notifyItemInserted(this.devices.size());
    }

    public void removeDevice(BluetoothDevice device) {
        int position = this.devices.indexOf(device);

        if (this.devices.remove(device)) {
            notifyItemRemoved(position);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bluetooth_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BluetoothDevice device = devices.get(position);
        holder.name.setText(String.valueOf(device.getName()));
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.bluetooth_item_name);

            itemView.setOnClickListener(l -> {
                int position = getAdapterPosition();
                BluetoothDevice device = devices.get(position);

                device.createBond();
            });
        }
    }
}
