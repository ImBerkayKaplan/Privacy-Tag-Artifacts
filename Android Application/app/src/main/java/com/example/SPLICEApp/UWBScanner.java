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

import org.w3c.dom.Text;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class UWBScanner extends AppCompatActivity {
    private UsbSerialPort port = null;
    private UWBListener uwbListener = null;

    class UWBListener implements SerialInputOutputManager.Listener{

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onNewData(byte[] data) {
            String message = new String(data, StandardCharsets.UTF_8);
            TextView tv = (TextView) findViewById(R.id.UWBScannerStatus);
            tv.setText("Distance: " + message);
        }

        @Override
        public void onRunError(Exception e) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uwb_scanner_screen);

        TextView text = (TextView) findViewById(R.id.UWBScannerStatus);

        this.uwbListener = new UWBListener();

        this.port = connect();
        if (this.port == null){
            Toast.makeText(this, "Connection to UWB adapter failed", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Handler handler = new Handler();
        UsbSerialPort finalPort = port;
        final Runnable r = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void run() {
                handler.postDelayed(this, 800);

                int len = 0;
                byte buffer[] = new byte[8192];
                try {
                    len = finalPort.read(buffer, 200);
                    /*if (len == 6) {
                        receive(Arrays.copyOf(buffer, len), text);
                    }*/
                    String distance = new String(buffer, StandardCharsets.UTF_8);
                    distance = distance.trim();
                    if (distance.length() >= 4) {
                        distance = distance.substring(0, 4);
                        int decimal_index = distance.indexOf('.');
                        if (decimal_index != -1  && decimal_index != 0) {
                            text.setText("Distance: \n" + distance + " meters");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(r, 0000);
    }

    private void receive(byte[] data, TextView txt) {
        String message = dumpHexString(data);
        txt.setText(message);
    }

    public static String dumpHexString(byte[] array) {
        StringBuilder result = new StringBuilder();

        byte[] line = new byte[8];
        int lineIndex = 0;

        for (int i = 0; i < 0 + array.length; i++) {
            if (lineIndex == line.length) {
                for (int j = 0; j < line.length; j++) {
                    if (line[j] > ' ' && line[j] < '~') {
                        result.append(new String(line, j, 1));
                    } else {
                        result.append(".");
                    }
                }
                result.append("\n");
                lineIndex = 0;
            }
            byte b = array[i];
            line[lineIndex++] = b;
        }
        for (int i = 0; i < lineIndex; i++) {
            if (line[i] > ' ' && line[i] < '~') {
                result.append(new String(line, i, 1));
            } else {
                result.append(" ");
            }
        }
        return result.toString();
    }

    private UsbSerialProber getCustomProbe() {
        ProbeTable customTable = new ProbeTable();
        customTable.addProduct(0x1366, 0x0105, CdcAcmSerialDriver.class);
        return new UsbSerialProber(customTable);
    }

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

        /*this.port = usbSerialPort;
        this.uwbListener = new UWBListener();

        SerialInputOutputManager usbIoManager = new SerialInputOutputManager(usbSerialPort, uwbListener);
        usbIoManager.start();*/

    }
}
