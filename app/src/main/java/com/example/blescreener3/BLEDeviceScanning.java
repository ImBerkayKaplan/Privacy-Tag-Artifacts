package com.example.blescreener3;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Build;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.HashSet;
import java.util.Set;

@RequiresApi(21)
class BLEDeviceScanning {

    Set<String> addresses;
    Set<String> addresses_on_screen;
    LinearLayout linearLayout;
    Context context;

    BLEDeviceScanning(LinearLayout linearLayout, Context context){

        // Initialize the global variables
        this.addresses = new HashSet<String>();
        this.addresses_on_screen = new HashSet<String>();
        this.linearLayout = linearLayout;
        this.context = context;

        // Add the addresses into the set that can appear
        addresses.add("AC:23:3F:77:28:61");
        addresses.add("AC:23:3F:77:28:62");
        addresses.add("AC:23:3F:77:28:63");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void startScanning(){

        // Prepare the bluetooth adapter and scanner
        BluetoothLeScanner bluetoothLeScanner = BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner();
        final BluetoothManager bluetoothManager = (BluetoothManager) this.context.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

        // Check if BLE is present in the device
        if (bluetoothAdapter == null) {
            Toast.makeText(context, "This device doesn't support BLE.", Toast.LENGTH_LONG).show();
            return;
        }

        // Check if BLE is enabled
        if (!bluetoothAdapter.isEnabled()) {
            Toast.makeText(context, "Bluetooth is disabled.", Toast.LENGTH_LONG).show();
        }

        bluetoothLeScanner.startScan(leScanCallback);
    }

    // BLE Device scan callback.
    private ScanCallback leScanCallback = new ScanCallback() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            String address = result.getDevice().getAddress();
            String rsi = String.valueOf(result.getRssi());
            if(addresses.contains(address)) {
                if(addresses_on_screen.contains(address)) {
                    for (int i = 0; i < linearLayout.getChildCount(); i++){
                        TextView view = (TextView) linearLayout.getChildAt(i);
                        String viewText = (String) view.getText();
                        if(viewText.startsWith(address)){
                            view.setText(viewText.substring(0, viewText.lastIndexOf(' ') + 1) + rsi);
                            break;
                        }
                    }
                }else{
                    TextView textView = new TextView(context);
                    textView.setText(address + ", RSI: " + rsi);
                    linearLayout.addView(textView);
                    addresses_on_screen.add(address);
                }
            }
        }
    };
}
