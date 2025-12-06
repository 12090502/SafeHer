package uk.ac.tees.mad.planty.notificaion

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

import uk.ac.tees.mad.safeher.R
import java.util.Calendar

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create notification channel (for Android O and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "safeher_alert_channel",
                "SafeHer Safety Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Regular safety reminders and alerts from SafeHer"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val message = intent?.getStringExtra("message")
            ?: "Stay alert and share your location with trusted contacts. 🚨"

        val notification = NotificationCompat.Builder(context, "safeher_alert_channel")
            .setContentTitle("SafeHer ⚠️")
            .setContentText(message)
            .setSmallIcon(R.mipmap.ic_launcher) // replace with your SafeHer app icon
            .setAutoCancel(true)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}


fun scheduleDailySafetyReminders(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    // Messages for SafeHer safety reminders
    val messages = listOf(
        "Remember to check your trusted contacts and keep them updated. 👥",
        "Stay aware of your surroundings — safety first! ⚠️",
        "Ensure your location sharing is active when traveling alone. 📍"
    )

    // Set reminder times (8 AM, 12 PM, 6 PM)
    val times = listOf(8 to 0, 12 to 0, 18 to 0)

    for (i in times.indices) {
        val (hour, minute) = times[i]

        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("message", messages[i % messages.size])
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            i,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }
}


fun showInstantNotification(context: Context) {
    val intent = Intent(context, NotificationReceiver::class.java).apply {
        putExtra("message", "⚠️ Stay safe! Share your location with trusted contacts if needed.")
    }
    context.sendBroadcast(intent)
}