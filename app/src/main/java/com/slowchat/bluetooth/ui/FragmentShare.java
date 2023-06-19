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

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.slowchat.R;
import com.slowchat.bluetooth.domain.BluetoothModel;
import com.slowchat.bluetooth.domain.BluetoothViewModel;
import com.slowchat.bluetooth.service.BluetoothConstants;
import com.slowchat.bluetooth.service.BluetoothService;

public class FragmentShare extends Fragment {
    private Context context;
    private BluetoothService bluetoothService;

    private BluetoothViewModel viewModel;
    private BluetoothItemAdapter pairedDevicesAdapter;
    private BluetoothAvailableItemAdapter availableDevicesAdapter;

    private LinearLayout pairedView;
    private LinearLayout availableView;
    private Button allowBluetoothButton;
    private Button enableBluetoothButton;
    private MaterialCardView allowCardView;
    private MaterialCardView enableCardView;
    private ExtendedFloatingActionButton searchButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = requireContext();
        bluetoothService = new BluetoothService(requireActivity(), handler);
        viewModel = new BluetoothViewModel(bluetoothService);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_share, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pairedView = view.findViewById(R.id.fragment_share_paired_view);
        availableView = view.findViewById(R.id.fragment_share_available_view);

        allowBluetoothButton = view.findViewById(R.id.fragment_share_allow_bluetooth);
        enableBluetoothButton = view.findViewById(R.id.fragment_share_enable_bluetooth);

        allowCardView = view.findViewById(R.id.fragment_share_allow_bluetooth_card);
        enableCardView = view.findViewById(R.id.fragment_share_enable_bluetooth_card);

        searchButton = view.findViewById(R.id.fragment_share_search);
        searchButton.setOnClickListener(l -> showAvailableDevices());

        pairedDevicesAdapter = new BluetoothItemAdapter(bluetoothService);
        availableDevicesAdapter = new BluetoothAvailableItemAdapter();

        RecyclerView recyclerViewPaired = view.findViewById(R.id.fragment_share_recyclerview_paired);
        recyclerViewPaired.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerViewPaired.setHasFixedSize(false);
        recyclerViewPaired.setNestedScrollingEnabled(false);
        recyclerViewPaired.setAdapter(pairedDevicesAdapter);

        RecyclerView recyclerViewAvailable = view.findViewById(R.id.fragment_share_recyclerview_available);
        recyclerViewAvailable.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerViewAvailable.setHasFixedSize(false);
        recyclerViewAvailable.setNestedScrollingEnabled(false);
        recyclerViewAvailable.setAdapter(availableDevicesAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        requireActivity().registerReceiver(receiver, intentFilter);

        showPairedDevices();
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.cancelDiscovery();
        requireActivity().unregisterReceiver(receiver);
    }

    private void showPairedDevices() {
        if (!isBluetoothAllowed() || isBluetoothDisabled()) {
            return;
        }

        pairedDevicesAdapter.setDevices(viewModel.getPairedDevices());
        pairedView.setVisibility(View.VISIBLE);
        searchButton.show();
    }

    private void showAvailableDevices() {
        boolean bluetoothScanAllowed = isBluetoothScanAllowed();
        boolean bluetoothDisabled = isBluetoothDisabled();

        if (!bluetoothScanAllowed || bluetoothDisabled) {
            return;
        }

        searchButton.hide();
        availableView.setVisibility(View.VISIBLE);
        viewModel.startDiscovery();
    }

    private boolean isBluetoothAllowed() {
        if (!viewModel.isBluetoothAllowed()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                allowBluetoothButton.setOnClickListener(v -> allowBluetoothLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT));
            } else {
                allowBluetoothButton.setOnClickListener(v -> allowBluetoothLauncher.launch(Manifest.permission.BLUETOOTH));
            }

            allowCardView.setVisibility(View.VISIBLE);
            return false;
        }

        allowCardView.setVisibility(View.GONE);
        return true;
    }

    private boolean isBluetoothScanAllowed() {
        if (!viewModel.isBluetoothScanAllowed()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                allowBluetoothScanLauncher.launch(Manifest.permission.BLUETOOTH_SCAN);
            } else {
                allowBluetoothScanLauncher.launch(Manifest.permission.BLUETOOTH);
            }

            return false;
        }

        return true;
    }

    private boolean isBluetoothDisabled() {
        if (!viewModel.isBluetoothEnabled()) {
            enableBluetoothButton.setOnClickListener(v -> enableBluetoothLauncher.launch(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)));

            enableCardView.setVisibility(View.VISIBLE);
            return true;
        }

        enableCardView.setVisibility(View.GONE);
        return false;
    }

    private final ActivityResultLauncher<Intent> enableBluetoothLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    showPairedDevices();
                }
            });

    private final ActivityResultLauncher<String> allowBluetoothScanLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    showAvailableDevices();
                }
            });

    private final ActivityResultLauncher<String> allowBluetoothLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    showPairedDevices();
                }
            });

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                int intExtra = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, -1);

                switch (intExtra) {
                    case BluetoothDevice.BOND_NONE:
                        Toast.makeText(context, getResources().getText(R.string.connection_error), Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothDevice.BOND_BONDING:
                        Toast.makeText(context, getResources().getText(R.string.connection_pending), Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothDevice.BOND_BONDED:
                        Toast.makeText(context, getResources().getText(R.string.successfully_connected), Toast.LENGTH_SHORT).show();

                        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                        pairedDevicesAdapter.addDevice(device);
                        availableDevicesAdapter.removeDevice(device);
                        break;
                }
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // TODO: check if the device is not already paired
                availableDevicesAdapter.addDevice(device);
            }
        }
    };

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        private boolean dataSent = false;

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case BluetoothConstants.MESSAGE_WRITE:
                    dataSent = true;
                    Toast.makeText(context, "Sent successfully", Toast.LENGTH_SHORT).show();
                    break;

                case BluetoothConstants.MESSAGE_READ:
                    BluetoothModel bluetoothModel = (BluetoothModel) msg.obj;

                    viewModel.insertMessages(bluetoothModel.getMessages());
                    viewModel.insertReceipts(bluetoothModel.getReceipts());
                    viewModel.insertLetterBoxes(bluetoothModel.getLetterBoxes());

                    Toast.makeText(context, "Received successfully", Toast.LENGTH_SHORT).show();

                    if (!dataSent) {
                        dataSent = true;
                        bluetoothService.sendData();
                    }

                    break;

                case BluetoothConstants.MESSAGE_DEVICE_NAME:
                    String connectedDeviceName = msg.getData().getString(BluetoothConstants.DEVICE_NAME);
                    Toast.makeText(context, "Connected to " + connectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;

                case BluetoothConstants.MESSAGE_TOAST:
                    Toast.makeText(context, msg.getData().getString(BluetoothConstants.TOAST), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
