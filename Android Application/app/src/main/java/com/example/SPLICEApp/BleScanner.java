package com.example.SPLICEApp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class BleScanner extends AppCompatActivity {

    private static final HashMap<String, String> db = new HashMap<>();
    private final HashMap<View, byte[]> trigger_by_address = new HashMap<>();
    private final BluetoothLeScanner bluetoothLeScanner = BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner();
    private USBConnection USB;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_screen);
        this.USB = USBConnection.Companion.getInstance(getApplicationContext());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onStart(){
        super.onStart();
        ScanBLEDevices();
        TextView tv = findViewById(R.id.NumDevices);
        tv.setText(MessageFormat.format("Devices Found: {0}", db.size()));

        // Open the UWB port
        try {
            this.USB.connect(findViewById(R.id.UWBDistance));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        db.clear();
        bluetoothLeScanner.stopScan(bleScanCallback);
        trigger_by_address.clear();
        TableLayout table = findViewById(R.id.MAC_RSSI);
        table.removeAllViews();
    }

    private void ScanBLEDevices(){

        // Prepare the bluetooth adapter and scanner
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

        // Ensures Bluetooth is available on the device and it is enabled. If not, displays a dialog requesting user permission to enable Bluetooth.
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }

        bluetoothLeScanner.startScan(bleScanCallback);
    }

    // BLE Device scan callback.
    private final ScanCallback bleScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            // Remove all beacons that don't conform to our standards
            byte[] beacon = result.getScanRecord().getBytes();
            int our_beacon = 0, uwb_or_buzzer;
            for(int i = 9; i < beacon.length && i < 25; i++){
                our_beacon += beacon[i];
                //Log.e("Beacon:", String.valueOf(beacon[i]));
            }
            //Log.e("Our Beacon Sum:", String.valueOf(our_beacon));
            if(our_beacon != -50) return;
            Log.e("Beacon:", String.valueOf(beacon[9]));
            Log.e("Beacon:", String.valueOf(beacon[10]));
            Log.e("Beacon:", "\n");
            uwb_or_buzzer = beacon[9];

            // Obtain the address of the device, and keep a record of when it was received
            String deviceID = result.getDevice().getAddress();

            // infers distance by RSSI
            int temp = result.getRssi();
            String deviceRSSI;
            if(temp > -50){
                deviceRSSI = "Less than 1 meter away";
            }else if(temp >-60){
                deviceRSSI = "Less than 2 meter away";
            }else{
                deviceRSSI = "More than 2 meters away";
            }
            String finalDeviceRSSI = deviceRSSI;

            if (!db.containsKey(deviceID)) {
                // new device, add row to table
                db.put(deviceID, deviceRSSI);

                //@Override
                runOnUiThread(() -> {
                    // update UI for num of devices
                    TextView tv = findViewById(R.id.NumDevices);
                    tv.setText(MessageFormat.format("Devices Found: {0}", db.size()));

                    // update UI for MAC-RSSI database
                    TableLayout tl = findViewById(R.id.MAC_RSSI);
                    TableRow tr_head = new TableRow(getApplicationContext());

                    TextView MAC_view = new TextView(getApplicationContext());
                    LinearLayout.LayoutParams MAC_params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
                    MAC_params.setMargins(0, 1, 1, 1);
                    MAC_view.setLayoutParams(MAC_params);
                    MAC_view.setText(deviceID);
                    MAC_view.setTextColor(Color.parseColor("#000000"));
                    MAC_view.setAlpha(0.54f);
                    MAC_view.setGravity(Gravity.CENTER);
                    MAC_view.setBackgroundColor(Color.WHITE);
                    tr_head.addView(MAC_view);

                    TextView RSSI_view = new TextView(getApplicationContext());
                    LinearLayout.LayoutParams RSSI_params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
                    RSSI_params.setMargins(1, 1, 1, 1);
                    RSSI_view.setLayoutParams(RSSI_params);
                    RSSI_view.setText(finalDeviceRSSI);
                    RSSI_view.setTextColor(Color.parseColor("#000000"));
                    RSSI_view.setAlpha(0.54f);
                    RSSI_view.setGravity(Gravity.CENTER);
                    RSSI_view.setBackgroundColor(Color.WHITE);
                    tr_head.addView(RSSI_view);

                    Button trigger_button = new Button(getApplicationContext());
                    LinearLayout.LayoutParams trigger_params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
                    trigger_button.setLayoutParams(trigger_params);

                    // Determine if the incoming beacon is from acoustic or UWB board
                    if (uwb_or_buzzer == -1){
                        trigger_button.setText(R.string.activate_sound);
                        trigger_button.setOnClickListener(v -> sendBeacon(v));
                    }else {
                        trigger_button.setText(R.string.activate_uwb);
                        trigger_button.setOnClickListener(v -> {
                        });
                    }

                    trigger_button.setAlpha(0.54f);
                    trigger_button.setGravity(Gravity.CENTER);
                    trigger_button.setBackgroundColor(Color.GREEN);
                    tr_head.addView(trigger_button);

                    tl.addView(tr_head, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                    trigger_by_address.put(trigger_button, Arrays.copyOfRange(result.getScanRecord().getBytes(), 9, 25));
                });

                } else {
                    db.put(deviceID, deviceRSSI);
                    runOnUiThread(() -> {
                        // update UI for existing MAC with new RSSI value
                        TableLayout tl = findViewById(R.id.MAC_RSSI);
                        for (int i = 0; i < tl.getChildCount(); i++) {
                            TableRow tr = (TableRow) tl.getChildAt(i);
                            TextView tv1 = (TextView) tr.getChildAt(0);
                            TextView tv2 = (TextView) tr.getChildAt(1);
                            String curDeviceID = tv1.getText().toString();
                            if (curDeviceID.equals(deviceID)) {
                                tv2.setText(finalDeviceRSSI);
                            }
                        }
                    });
            }
        }
    };

    public void sendBeacon(View view) {
        BluetoothLeAdvertiser advertiser = BluetoothAdapter.getDefaultAdapter().getBluetoothLeAdvertiser();
        ParcelUuid pUuid = new ParcelUuid(UUID.fromString(getString(R.string.ble_uuid)));

        byte[] viewValue = trigger_by_address.get(view);
        AdvertiseData data = (new AdvertiseData.Builder())
                .setIncludeDeviceName(true)
                .addServiceData(pUuid, Arrays.copyOfRange(Objects.requireNonNull(viewValue), 0, 6))
                .build();

        AdvertiseSettings settings = new AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
                .setConnectable(false)
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
                Log.e("BLE", "Advertising onStartFailure: " + errorCode);
                super.onStartFailure(errorCode);
            }
        };
        advertiser.startAdvertising(settings, data, advertisingCallback);
    }
}