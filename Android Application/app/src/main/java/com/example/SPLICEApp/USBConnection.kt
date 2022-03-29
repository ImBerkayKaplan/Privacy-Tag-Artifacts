package com.example.SPLICEApp

import android.app.PendingIntent
import android.content.Context
import android.content.Context.USB_SERVICE
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Build
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.hoho.android.usbserial.driver.*
import com.hoho.android.usbserial.util.SerialInputOutputManager
import java.io.IOException
import java.nio.ByteBuffer


// Use applicationContext for the context variable
class USBConnection(private val context: Context) : SerialInputOutputManager.Listener{
    private var port: UsbSerialPort? = null
    private lateinit var uwbDistanceText: TextView

    private fun getCustomProbe(): UsbSerialProber {
        val customTable = ProbeTable()
        customTable.addProduct(0x1366, 0x0105, CdcAcmSerialDriver::class.java)
        return UsbSerialProber(customTable)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @Throws(IOException::class)
    fun connect(uwbDistanceText: TextView){
        this.uwbDistanceText = uwbDistanceText
        // Ensure connect was never called before
        if (this.port != null) return

        val usbManager: UsbManager = context.getSystemService(USB_SERVICE) as UsbManager
        if (usbManager.deviceList.isEmpty()) {
            val toast = Toast.makeText(context, "No USB device is connected", Toast.LENGTH_SHORT)
            toast.show()
            return
        }

        val device = usbManager.deviceList.values.toTypedArray()[0] as UsbDevice
        val driver: UsbSerialDriver = getCustomProbe().probeDevice(device)

        if (!usbManager.hasPermission(driver.device)) {
            val usbPermissionIntent = PendingIntent.getBroadcast(
                context,
                0,
                Intent(BuildConfig.APPLICATION_ID + ".GRANT_USB"),
                PendingIntent.FLAG_IMMUTABLE
            )
            usbManager.requestPermission(driver.device, usbPermissionIntent)
        }

        val usbSerialPort = driver.ports[0]
        if (!usbManager.hasPermission(driver.device)) return
        val usbConnection = usbManager.openDevice(driver.device)

        try {
            usbSerialPort.open(usbConnection)
            usbSerialPort.setParameters(115200, 8, 1, UsbSerialPort.PARITY_NONE)
            usbSerialPort.dtr = true
        } catch (e: java.lang.Exception) {
            val toast = Toast.makeText(
                context,
                "Could not open USB port. If you just gave your permission now, try again.",
                Toast.LENGTH_SHORT
            )
            toast.show()
            return
        }

        this.port = usbSerialPort
        val usbIoManager = SerialInputOutputManager(usbSerialPort, this)
        usbIoManager.start()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onNewData(data: ByteArray?) {
        if (data == null || data.isEmpty()) return
        val buffer = ByteBuffer.wrap(data)
        val float1 = buffer.getFloat(0).times(1000000000.0).toString().substring(0,5)
        Log.e("onNewData:", float1)
        this.uwbDistanceText.text = float1
    }

    override fun onRunError(e: Exception?) {
        this.port = null
    }
}