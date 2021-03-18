package com.example.blescreener3;

import android.app.Activity;
import android.content.Context;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

@RequiresApi(21)
public class NFCTagScanning {
    Context context;
    Activity activity;
    NFCTagScanning(Activity activity, Context context){
        this.context = context;
        this.activity = activity;
    }

    void startScanning(){
        NfcAdapter mNfcAdapter = NfcAdapter.getDefaultAdapter(context);

        // Check if NFC is present
        if (mNfcAdapter == null) {
            Toast.makeText(context, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            return;
        }

        // Check if NFC is enabled
        if (!mNfcAdapter.isEnabled()) {
            Toast.makeText(context, "NFC is disabled.", Toast.LENGTH_LONG).show();
        }

        // Start the callback function for NFC listening
        mNfcAdapter.enableReaderMode(activity, nfcScanCallback, 1, null);

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
            }
        }
    };
}
