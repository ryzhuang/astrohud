package com.example.raymo.astrohud;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.UUID;

/**
 * Created by raymo on 4/24/2016.
 */
public class BluetoothHelper {

    private BluetoothDevice mBluetoothDevice;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothSocket mBluetoothSocket;
    private InputStream mInputStream;

    public BluetoothHelper() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().equals("HC-05")) //Note, you will need to change this to match the name of your device
                {
                    mBluetoothDevice = device;
                    break;
                }
            }
        }
    }

    public void connectDevice() {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
        try {
            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(uuid);
            mBluetoothSocket.connect();
            mInputStream = mBluetoothSocket.getInputStream();
            if (mBluetoothSocket != null) {
                Log.d("BLUETOOTH", "connectDevice: CONNECTED");
            } else {
                Log.d("BLUETOOTH", "connectDevice: NOTCONNECTED");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getInput() {
        BufferedReader r = new BufferedReader(new InputStreamReader(mInputStream));
        StringBuilder total = new StringBuilder();
        String line;
        try {
            while ((line = r.readLine()) != null) {
                return line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
