package com.example.blescreener3;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ScanBLEDevices();
        //ScanNFCDevices();
    }

    public void switchLocationMode(View view) {
        Intent intent = new Intent(this, BleScanner.class);
        startActivity(intent);
    }

    public void switchInventoryMode(View view) {
        Intent intent = new Intent(this, NfcScanner.class);
        startActivity(intent);
    }

    private void ScanNFCDevices(){
        NfcAdapter mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
            return;

        }

        if (!mNfcAdapter.isEnabled()) {
            Toast.makeText(this, "NFC is disabled.", Toast.LENGTH_LONG).show();
        }

        mNfcAdapter.enableReaderMode(this, nfcScanCallback, 1, null);

    }

    // NFC tag scan callback
    private NfcAdapter.ReaderCallback nfcScanCallback = new NfcAdapter.ReaderCallback(){
        @Override
        public void onTagDiscovered(Tag tag) {
            Ndef ndef = Ndef.get(tag);
            try {
                ndef.connect();
                NdefMessage ndefMessage= ndef.getNdefMessage();
                for(NdefRecord ndefRecord : ndefMessage.getRecords()){
                    Log.i("TAG", new String(ndefRecord.getPayload()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    };
}