package com.slowchat.bluetooth.service;

import static android.Manifest.permission.BLUETOOTH;
import static android.Manifest.permission.BLUETOOTH_CONNECT;
import static android.Manifest.permission.BLUETOOTH_SCAN;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.slowchat.R;
import com.slowchat.bluetooth.domain.BluetoothModel;
import com.slowchat.letterbox.domain.LetterBoxModel;
import com.slowchat.letterbox.domain.LetterBoxViewModel;
import com.slowchat.message.domain.ChatViewModel;
import com.slowchat.message.domain.models.MessageModel;
import com.slowchat.receipt.domain.ReceiptModel;
import com.slowchat.receipt.domain.ReceiptViewModel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BluetoothService {
    public static final String TAG = "BluetoothService";
    private static final String NAME = "SlowChat";
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public static final int STATE_NONE = 0;
    public static final int STATE_LISTEN = 1;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_CONNECTED = 3;

    public static final int REQUEST_BLUETOOTH = 100;
    public static final int REQUEST_BLUETOOTH_DISCOVERABLE = 101;

    private final Activity activity;
    private final Handler handler;
    private final BluetoothAdapter adapter;

    private static int state = STATE_NONE;
    private static AcceptThread acceptThread;
    private static ConnectThread connectThread;
    private static ConnectedThread connectedThread;

    public BluetoothService(Activity activity, Handler handler) {
        this.activity = activity;
        this.handler = handler;

        adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            new MaterialAlertDialogBuilder(activity)
                    .setTitle(R.string.no_bluetooth)
                    .setMessage(R.string.no_bluetooth_description)
                    .setPositiveButton(android.R.string.ok, null)
                    .create()
                    .show();
        }

        if (isBluetoothAllowed() && state == BluetoothService.STATE_NONE) {
            start();
        }
    }

    public synchronized int getState() {
        return state;
    }

    public synchronized void start() {
        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }

        if (acceptThread == null) {
            acceptThread = new AcceptThread();
            acceptThread.start();
        }
    }

    public synchronized void connect(BluetoothDevice device) {
        if (state == STATE_CONNECTING) {
            if (connectThread != null) {
                connectThread.cancel();
                connectThread = null;
            }
        }

        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }

        connectThread = new ConnectThread(device);
        connectThread.start();
    }

    @SuppressLint("MissingPermission")
    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }

        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }

        if (acceptThread != null) {
            acceptThread.cancel();
            acceptThread = null;
        }

        connectedThread = new ConnectedThread(socket);
        connectedThread.start();

        Message message = handler.obtainMessage(BluetoothConstants.MESSAGE_DEVICE_NAME);
        Bundle bundle = new Bundle();
        bundle.putString(BluetoothConstants.DEVICE_NAME, device.getName());
        message.setData(bundle);
        handler.sendMessage(message);
    }

    public synchronized void stop() {
        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }

        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }

        if (acceptThread != null) {
            acceptThread.cancel();
            acceptThread = null;
        }

        state = STATE_NONE;
    }

    public void write(byte[] bytes) {
        ConnectedThread connectedThread1;

        synchronized (this) {
            if (state != STATE_CONNECTED) {
                return;
            }

            connectedThread1 = connectedThread;
        }

        connectedThread1.write(bytes);
    }

    private void connectionFailed() {
        state = STATE_LISTEN;

        Message message = handler.obtainMessage(BluetoothConstants.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(BluetoothConstants.TOAST, "Unable to connect device");
        message.setData(bundle);
        handler.sendMessage(message);
    }

    private void connectionLost() {
        state = STATE_LISTEN;

        Message message = handler.obtainMessage(BluetoothConstants.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(BluetoothConstants.TOAST, "Device connection was lost");
        message.setData(bundle);
        handler.sendMessage(message);
    }

    @SuppressLint("MissingPermission")
    private class AcceptThread extends Thread {
        private final BluetoothServerSocket serverSocket;

        public AcceptThread() {
            BluetoothServerSocket serverSocket1 = null;

            try {
                serverSocket1 = adapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
            } catch (IOException e) {
                Log.e(TAG, "Socket listen() failed", e);
            }

            serverSocket = serverSocket1;
            state = STATE_LISTEN;
        }

        @Override
        public void run() {
            setName("AcceptThread");
            BluetoothSocket socket;

            while (state != STATE_CONNECTED) {
                try {
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    Log.e(TAG, "Socket accept() failed", e);
                    break;
                }

                if (socket != null) {
                    synchronized (BluetoothService.this) {
                        switch (state) {
                            case STATE_LISTEN:
                            case STATE_CONNECTING:
                                connected(socket, socket.getRemoteDevice());
                                break;
                            case STATE_NONE:
                            case STATE_CONNECTED:
                                try {
                                    socket.close();
                                } catch (IOException e) {
                                    Log.e(TAG, "Could not close unwanted socket", e);
                                }

                                break;
                        }
                    }
                }
            }
        }

        public void cancel() {
            try {
                serverSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Socket close() of server failed", e);
            }
        }
    }

    @SuppressLint("MissingPermission")
    private class ConnectThread extends Thread {
        private final BluetoothSocket socket;
        private final BluetoothDevice device;

        public ConnectThread(BluetoothDevice device) {
            this.device = device;
            BluetoothSocket socket1 = null;

            try {
                socket1 = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                Log.e(TAG, "Socket create() failed", e);
            }

            Message message = handler.obtainMessage(BluetoothConstants.MESSAGE_TOAST);
            Bundle bundle = new Bundle();
            bundle.putString(BluetoothConstants.TOAST, "Connecting...");
            message.setData(bundle);
            handler.sendMessage(message);

            socket = socket1;
            state = STATE_CONNECTING;
        }

        public void run() {
            setName("ConnectThread");

            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED) {
                adapter.cancelDiscovery();
            }

            try {
                socket.connect();
            } catch (IOException e) {
                connectionFailed();

                try {
                    socket.close();
                } catch (IOException ex) {
                    Log.e(TAG, "Unable to close() socket during connection failure", ex);
                }

                BluetoothService.this.start();
                return;
            }

            synchronized (BluetoothService.this) {
                connectThread = null;
            }

            connected(socket, device);
        }

        public void cancel() {
            try {
                socket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket socket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public ConnectedThread(BluetoothSocket socket) {
            this.socket = socket;

            InputStream inputStream1 = null;
            OutputStream outputStream1 = null;

            try {
                inputStream1 = socket.getInputStream();
                outputStream1 = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "Sockets not created", e);
            }

            inputStream = inputStream1;
            outputStream = outputStream1;
            state = STATE_CONNECTED;
        }

        public void run() {
            BluetoothModel bluetoothModel;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            while (state == STATE_CONNECTED) {
                try {
                    if (inputStream.available() > 0) {
                        byteArrayOutputStream.write(inputStream.read());
                    } else {
                        if (byteArrayOutputStream.size() > 0) {
                            byte[] bytes = byteArrayOutputStream.toByteArray();
                            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));

                            bluetoothModel = (BluetoothModel) objectInputStream.readObject();
                            handler.obtainMessage(BluetoothConstants.MESSAGE_READ, -1, -1, bluetoothModel).sendToTarget();

                            byteArrayOutputStream.reset();
                        }
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Disconnected", e);
                    connectionLost();
                    break;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void write(byte[] bytes) {
            try {
                outputStream.write(bytes);
                handler.obtainMessage(BluetoothConstants.MESSAGE_WRITE).sendToTarget();
            } catch (IOException e) {
                Log.e(TAG, "Exception during write", e);
            }
        }

        public void cancel() {
            try {
                socket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }

    public void sendData() {
        List<MessageModel> messages = ChatViewModel.getMessages();
        List<LetterBoxModel> letterBoxes = LetterBoxViewModel.getLetterBoxes();
        List<ReceiptModel> receipts = ReceiptViewModel.getReceipts();

        BluetoothModel bluetoothModel = new BluetoothModel(messages, letterBoxes, receipts);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        try {
            ObjectOutputStream out = new ObjectOutputStream(bytes);
            out.writeObject(bluetoothModel);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        write(bytes.toByteArray());
    }

    public boolean isBluetoothEnabled() {
        return adapter.isEnabled();
    }

    public boolean isBluetoothAllowed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return ContextCompat.checkSelfPermission(activity, BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED;
        }

        return ContextCompat.checkSelfPermission(activity, BLUETOOTH) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean isBluetoothScanAllowed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return ContextCompat.checkSelfPermission(activity, BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED;
        }

        return ContextCompat.checkSelfPermission(activity, BLUETOOTH) == PackageManager.PERMISSION_GRANTED;
    }

    public Set<BluetoothDevice> getPairedDevices() {
        if (ContextCompat.checkSelfPermission(activity, BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(activity, new String[]{BLUETOOTH_CONNECT}, REQUEST_BLUETOOTH);
                return new HashSet<>();
            }
        }

        return adapter.getBondedDevices();
    }

    public void startDiscovery() {
        if (ActivityCompat.checkSelfPermission(activity, BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(activity, new String[]{BLUETOOTH_SCAN}, REQUEST_BLUETOOTH_DISCOVERABLE);
                return;
            }
        }

        adapter.startDiscovery();
    }

    public void cancelDiscovery() {
        if (ActivityCompat.checkSelfPermission(activity, BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED) {
            adapter.cancelDiscovery();
        }
    }
}
