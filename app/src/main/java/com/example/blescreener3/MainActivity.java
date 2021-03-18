package com.example.blescreener3;
import android.annotation.SuppressLint;
import android.content.Context;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity {
    Set<String> addresses = new HashSet<String>();
    Set<String> addresses_on_screen = new HashSet<String>();
    LinearLayout linearLayout;
    Context mainContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainContext = this;
        linearLayout = new LinearLayout(mainContext);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        setContentView(linearLayout);


        // Add the addresses into the set that can appear
        addresses.add("AC:23:3F:77:28:61");
        addresses.add("AC:23:3F:77:28:62");
        addresses.add("AC:23:3F:77:28:63");

        // Create threads to scan for BLE and NFC devices
        BLEDeviceScanning bleDeviceScanning = new BLEDeviceScanning(linearLayout, this);
        bleDeviceScanning.startScanning();
        NFCTagScanning nfcTagScanning = new NFCTagScanning(this, this);
        nfcTagScanning.startScanning();
    }
}