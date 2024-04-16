import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.aiivy782.cycles.Service

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val serviceIntent = Intent(context, Service::class.java)
            context?.startService(serviceIntent)
        }
    }
}

