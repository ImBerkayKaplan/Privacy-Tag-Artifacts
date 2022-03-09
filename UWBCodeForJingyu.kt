import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.USB_SERVICE

import java.io.IOException
import java.nio.charset.StandardCharsets


// Use applicationContext for the context variable
class UWBConnection : SerialInputOutputManager.Listener {

    private var port: UsbSerialPort? = null
    lateinit var context: Context

    private fun processPacket(data: List<String>){

    }

    private fun getCustomProbe(): UsbSerialProber {
        val customTable = ProbeTable()
        customTable.addProduct(0x1366, 0x0105, CdcAcmSerialDriver.class);
        return UsbSerialProber(customTable)
    }

    @Throws(IOException::class)
    fun connect(){

        // Ensure connect was never called before
        if (this.port != null){
            return
        }

        val usbManager = context.getSystemService(USB_SERVICE) as UsbManager

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
            var i = 0
            while (i < 10 && !usbManager.hasPermission(driver.device)){
                Thread.sleep(500)
                i++
            }
        }

        val usbSerialPort = driver.ports[0]
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

    override fun onNewData(data: ByteArray?) {
        val message: String? = data?.let { String(it, StandardCharsets.UTF_8).trim { it <= ' ' } }
        if (message != null) {
            Log.d("Message:", message)
            if (message.isNotEmpty()) {
                processPacket(message)
            }
        }
    }

    override fun onRunError(e: Exception?) {
        this.port = null
    }
}