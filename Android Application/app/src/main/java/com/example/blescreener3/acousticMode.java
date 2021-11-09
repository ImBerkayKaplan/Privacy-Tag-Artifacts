package com.example.blescreener3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.AdvertisingSet;
import android.bluetooth.le.AdvertisingSetCallback;
import android.bluetooth.le.AdvertisingSetParameters;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.View;

import java.util.UUID;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class acousticMode extends AppCompatActivity {
    private BluetoothLeScanner bluetoothLeScanner = BluetoothAdapter.getDefaultAdapter()
            .getBluetoothLeScanner();
    private boolean scanning;
    private Handler handler = new Handler();
    private long SCAN_PERIOD = 4000;

    AdvertisingSet currentAdvertisingSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acoustic_mode);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendBeacon(View view) {
        BluetoothLeAdvertiser advertiser = BluetoothAdapter.getDefaultAdapter()
                .getBluetoothLeAdvertiser();

        ParcelUuid pUuid = new ParcelUuid(UUID.fromString(getString(R.string.ble_uuid)));

        byte[] value = new byte[6];
        for (int i = 0; i < 6; i++) {
            value[i] = (byte) (0xFF);
        }

        AdvertiseData data = (new AdvertiseData.Builder())
                .setIncludeDeviceName(true)
                .addServiceData(pUuid, value)
                .build();

        /*AdvertisingSetParameters parameters = (new AdvertisingSetParameters.Builder())
                //.setLegacyMode(true)
                .setConnectable(false)
                .setScannable(true)
                .setInterval(AdvertisingSetParameters.INTERVAL_HIGH)
                .setTxPowerLevel(AdvertisingSetParameters.TX_POWER_MEDIUM)
                .build();

        AdvertisingSetCallback callback = new AdvertisingSetCallback() {
            @Override
            public void onAdvertisingSetStarted(AdvertisingSet advertisingSet, int txPower, int status) {
                Log.d("BLE", "Advertising started");
                currentAdvertisingSet = advertisingSet;
            }
        };

        advertiser.startAdvertisingSet(parameters, data, null, null, null, callback);*/

        AdvertiseSettings settings = new AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
                .setConnectable( false )
                .setTimeout(5000)
                .build();

        AdvertiseCallback advertisingCallback = new AdvertiseCallback() {
            @Override
            public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                super.onStartSuccess(settingsInEffect);
                Log.d("BLE", "Advertising started");
            }

            @Override
            public void onStartFailure(int errorCode) {
                Log.e( "BLE", "Advertising onStartFailure: " + errorCode);
                super.onStartFailure(errorCode);
            }
        };

        advertiser.startAdvertising(settings, data, advertisingCallback);

        // For server-client BLE data transfer
        /* if (!scanning) {
            // Stops scanning after a predefined scan period.
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scanning = false;
                    bluetoothLeScanner.stopScan(bleScanCallback);
                }
            }, SCAN_PERIOD);

            scanning = true;
            bluetoothLeScanner.startScan(bleScanCallback);
        } else {
            scanning = false;
            bluetoothLeScanner.stopScan(bleScanCallback);
        }*/
    }

    private ScanCallback bleScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            BluetoothDevice device = result.getDevice();
            BluetoothGatt bluetoothGatt = device.connectGatt(
                    acousticMode.this, false, bluetoothGattCallback);
            for (BluetoothGattService gattService : bluetoothGatt.getServices()) {
                BluetoothGattCharacteristic characteristic = gattService
                        .getCharacteristics().get(0);
                byte[] value = new byte[1];
                value[0] = (byte) (21 & 0xFF);  // this a random data sent to the BLE server.
                characteristic.setValue(value);
                bluetoothGatt.writeCharacteristic(characteristic);
            }  // TODO
        }
    };

    private BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                // successfully connected to the GATT Server
                gatt.discoverServices();
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                // disconnected from the GATT Server
                gatt.close();
            }
        }
    };
}