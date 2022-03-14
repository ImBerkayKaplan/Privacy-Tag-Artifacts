package com.example.SPLICEApp;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.hoho.android.usbserial.driver.CdcAcmSerialDriver;
import com.hoho.android.usbserial.driver.ProbeTable;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.hoho.android.usbserial.util.SerialInputOutputManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class UWBScanner extends AppCompatActivity {

    class UWBListener implements SerialInputOutputManager.Listener{

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onNewData(byte[] data) {
            String message = new String(data, StandardCharsets.UTF_8);
            TextView tv = findViewById(R.id.UWBScannerStatus);
            tv.setText(String.format("Distance: %s", message));
        }

        @Override
        public void onRunError(Exception e) {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uwb_scanner_screen);

        TextView text = findViewById(R.id.UWBScannerStatus);

        UsbSerialPort port = connect();
        if (port == null){
            Toast.makeText(this, "Connection to UWB adapter failed", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void run() {
                handler.postDelayed(this, 800);

                byte[] buffer = new byte[8192];
                try {
                    port.read(buffer, 200);
                    String distance = new String(buffer, StandardCharsets.UTF_8);
                    distance = distance.trim();
                    if (distance.length() >= 4) {
                        distance = distance.substring(0, 4);
                        int decimal_index = distance.indexOf('.');
                        if (decimal_index != -1  && decimal_index != 0) {
                            text.setText(String.format("Distance: \n%s meters", distance));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(r, 0);
    }

    private UsbSerialProber getCustomProbe() {
        ProbeTable customTable = new ProbeTable();
        customTable.addProduct(0x1366, 0x0105, CdcAcmSerialDriver.class);
        return new UsbSerialProber(customTable);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public UsbSerialPort connect() {
        UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

        if (usbManager.getDeviceList().isEmpty()) {
            Log.e("usb manager","No USB device is connected");
            return null;
        }

        UsbDevice device = (UsbDevice) usbManager.getDeviceList().values().toArray()[0];
        UsbSerialDriver driver = getCustomProbe().probeDevice(device);

        if (!usbManager.hasPermission(driver.getDevice())) {
            PendingIntent usbPermissionIntent = PendingIntent.getBroadcast(this,
                    0,
                    new Intent(BuildConfig.APPLICATION_ID + ".GRANT_USB"),
                    PendingIntent.FLAG_IMMUTABLE);
            usbManager.requestPermission(driver.getDevice(), usbPermissionIntent);
            int i = 0;
            while (i < 10 && !usbManager.hasPermission(driver.getDevice())){
                try {
                    Thread.sleep(500);
                    i++;
                } catch (InterruptedException e) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Could not open USB port. If you just gave your permission now, try again.",
                            Toast.LENGTH_SHORT
                    ).show();
                    return null;
                }
            }
        }

        UsbSerialPort usbSerialPort = driver.getPorts().get(0);
        UsbDeviceConnection usbConnection = usbManager.openDevice(driver.getDevice());


        try {
            usbSerialPort.open(usbConnection);
            usbSerialPort.setParameters(115200, 8, 1,
                    UsbSerialPort.PARITY_NONE);
            usbSerialPort.setDTR(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return usbSerialPort;
    }
}
