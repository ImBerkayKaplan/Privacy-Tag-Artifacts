package com.example.SPLICEApp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.MessageFormat;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NfcScanner extends AppCompatActivity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_screen);
        tv = findViewById(R.id.DeviceURL);
        ScanNFCDevices();
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
    private final NfcAdapter.ReaderCallback nfcScanCallback = new NfcAdapter.ReaderCallback(){
        @Override
        public void onTagDiscovered(Tag tag) {
            Ndef ndef = Ndef.get(tag);
            try {
                ndef.connect();
                NdefMessage ndefMessage= ndef.getNdefMessage();

                if (ndefMessage != null) {
                    runOnUiThread(() -> {
                        for (NdefRecord ndefRecord : ndefMessage.getRecords()) {
                            tv.setText(MessageFormat.format("Device URL: {0}", new String(ndefRecord.getPayload())));
                        }
                    });
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    };
}