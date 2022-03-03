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
import android.os.Handler;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class BleScanner extends AppCompatActivity {

    private static final HashMap<String, String> db = new HashMap<>();
    private final HashMap<View, byte[]> trigger_by_address = new HashMap<>();

    private final HashMap<String, Long> last_scanned_time_by_address = new HashMap<>();
    private final Handler handler = new Handler();
    private final Runnable remove_older_address_thread = new Runnable() {
        @Override
        public void run() {
            long current_time = System.currentTimeMillis();
            ArrayList<String> addresses_to_remove = new ArrayList<>();
            for (Map.Entry<String, Long> entry : last_scanned_time_by_address.entrySet()) {
                String address = entry.getKey();
                long last_scanned_time = entry.getValue();
                if (current_time - last_scanned_time > 10000) {
                    Log.e("Remove", address);
                    db.remove(address);
                    addresses_to_remove.add(address);
                }
            }

            TableLayout table = findViewById(R.id.MAC_RSSI);
            for (String address : addresses_to_remove) {
                last_scanned_time_by_address.remove(address);
                for (int i = 1, j = table.getChildCount(); i < j; i++) {
                    TableRow row = (TableRow) (table.getChildAt(i));
                    if (((TextView) row.getChildAt(0)).getText().equals(address)) {
                        table.removeView(row);
                    }
                }
            }
            TextView tv = findViewById(R.id.NumDevices);
            tv.setText(MessageFormat.format("Devices Found: {0}", db.size()));

            handler.postDelayed(this, 10000);
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_screen);
        TextView tv = findViewById(R.id.NumDevices);
        tv.setText(MessageFormat.format("Devices Found: {0}", db.size()));

        ScanBLEDevices();
        handler.post(remove_older_address_thread);
    }

    private void ScanBLEDevices(){

        // Prepare the bluetooth adapter and scanner
        BluetoothLeScanner bluetoothLeScanner = BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner();
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
            int our_beacon = 0;
            for(int i = 0; i < beacon.length && i < 25; i++){
                our_beacon += beacon[i];
                //Log.e("Beacon:", String.valueOf(beacon[i]));
            }
            //Log.e("Our Beacon Sum:", String.valueOf(our_beacon));
            if(our_beacon != 797) return;

            // Obtain the address of the device, and keep a record of when it was received
            String deviceID = result.getDevice().getAddress();
            last_scanned_time_by_address.put(deviceID, System.currentTimeMillis());

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
                int finalOur_beacon = our_beacon;
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
                    if (finalOur_beacon == 797 && db.size() != 3){
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
                    trigger_by_address.put(trigger_button, result.getScanRecord().getBytes());
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

        byte[] value = new byte[6];
        for (int i = 0; i < 6; i++) {
            value[i] = Objects.requireNonNull(trigger_by_address.get(view))[i + 9];
        }

        AdvertiseData data = (new AdvertiseData.Builder())
                .setIncludeDeviceName(true)
                .addServiceData(pUuid, value)
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