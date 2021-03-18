package com.example.blescreener3;

import androidx.annotation.IdRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class BleScanner extends AppCompatActivity {
    private static HashMap<String, Integer> db = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ble_scanner);

        TextView tv = (TextView) findViewById(R.id.NumDevices);
        tv.setText("Devices Found: " + db.size());

        // populate UI with stored database
        TableLayout tl = (TableLayout) findViewById(R.id.MAC_RSSI);

        for (Map.Entry<String, Integer> entry : db.entrySet()) {
            TableRow tr_head = new TableRow(getApplicationContext());

            TextView MAC_view = new TextView(getApplicationContext());
            LinearLayout.LayoutParams MAC_params = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
            MAC_params.setMargins(0, 1, 1, 1);
            MAC_view.setLayoutParams(MAC_params);
            MAC_view.setText(entry.getKey());
            MAC_view.setGravity(Gravity.CENTER);
            MAC_view.setBackgroundColor(Color.WHITE);
            tr_head.addView(MAC_view);

            TextView RSSI_view = new TextView(getApplicationContext());
            LinearLayout.LayoutParams RSSI_params = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
            RSSI_params.setMargins(1, 1, 1, 1);
            RSSI_view.setLayoutParams(RSSI_params);
            RSSI_view.setText(String.valueOf(entry.getValue()));
            RSSI_view.setGravity(Gravity.CENTER);
            RSSI_view.setBackgroundColor(Color.WHITE);
            tr_head.addView(RSSI_view);

            tl.addView(tr_head, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }

        ScanBLEDevices();
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
    private ScanCallback bleScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            if(result.getDevice().getAddress().startsWith("AC:23:3F:77:28")) {
                String deviceID = result.getDevice().getAddress();
                int deviceRSSI = result.getRssi();
                Log.i("TAG", deviceID);
                Log.i("TAG", String.valueOf(deviceRSSI));

                if (!db.containsKey(deviceID)) {
                    // new device, add row to table
                    db.put(deviceID, deviceRSSI);

                    // update UI for num of devices
                    TextView tv = (TextView) findViewById(R.id.NumDevices);
                    tv.setText("Devices Found: " + db.size());

                    // update UI for MAC-RSSI database
                    TableLayout tl = (TableLayout) findViewById(R.id.MAC_RSSI);
                    TableRow tr_head = new TableRow(getApplicationContext());

                    TextView MAC_view = new TextView(getApplicationContext());
                    LinearLayout.LayoutParams MAC_params = new TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
                    MAC_params.setMargins(0, 1, 1, 1);
                    MAC_view.setLayoutParams(MAC_params);
                    MAC_view.setText(deviceID);
                    MAC_view.setGravity(Gravity.CENTER);
                    MAC_view.setBackgroundColor(Color.WHITE);
                    tr_head.addView(MAC_view);

                    TextView RSSI_view = new TextView(getApplicationContext());
                    LinearLayout.LayoutParams RSSI_params = new TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
                    RSSI_params.setMargins(1, 1, 1, 1);
                    RSSI_view.setLayoutParams(RSSI_params);
                    RSSI_view.setText(String.valueOf(deviceRSSI));
                    RSSI_view.setGravity(Gravity.CENTER);
                    RSSI_view.setBackgroundColor(Color.WHITE);
                    tr_head.addView(RSSI_view);

                    tl.addView(tr_head, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                } else {
                    // existing device, check whether RSSI changes;
                    if (!db.get(deviceID).equals(deviceRSSI)) {
                        // RSSI changes
                        db.put(deviceID, deviceRSSI);

                        // update UI for existing MAC with new RSSI value
                        TableLayout tl = (TableLayout) findViewById(R.id.MAC_RSSI);
                        for (int i = 0; i < tl.getChildCount(); i++) {
                            TableRow tr = (TableRow) tl.getChildAt(i);
                            TextView tv1 = (TextView) tr.getChildAt(0);
                            TextView tv2 = (TextView) tr.getChildAt(1);
                            String curDeviceID = tv1.getText().toString();
                            if (curDeviceID.equals(deviceID)) {
                                tv2.setText(String.valueOf(deviceRSSI));
                            }
                        }
                    }
                }
            }
        }
    };
}