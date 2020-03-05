package com.liveshopper.example

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.liveshopper.sdk.LiveShopper
import com.liveshopper.sdk.LiveShopperReceiver
import com.liveshopper.sdk.model.LSEvent
import com.liveshopper.sdk.model.LSUser
import com.liveshopper.example.utils.fromEvent
import com.liveshopper.example.utils.fromStatus

class ExampleReceiver : LiveShopperReceiver() {
    override fun onClientLocationUpdated(context: Context, location: Location, stopped: Boolean, source: LiveShopper.LSLocationSource) {
        var body = "${if (stopped) "client stopped at" else "client moved to"} location (${location.latitude}, " +
                        "${location.longitude}) with accuracy ${location.accuracy} and source $source"

        notify(context, body)
    }

    override fun onError(context: Context, status: LiveShopper.LSStatus) {
        val body = fromStatus(status)

        notify(context, body)
    }

    override fun onEventsReceived(context: Context, events: List<LSEvent>, user: LSUser) {
        events.forEach { event ->
            val body = "event = ${fromEvent(event)}; type = ${event.type};"

            notify(context, body)
        }
    }

    override fun onLocationUpdated(context: Context, location: Location, user: LSUser) {
        var body = "${if (user.stopped) "stopped at" else "moved to"} location (${location.latitude}, " +
                        "${location.longitude}) with accuracy ${location.accuracy}"

        notify(context, body)
    }

    override fun onLog(context: Context, message: String) {
        notify(context, message)
    }

    companion object {
        var identifier = 0

        internal fun notify(context: Context, body: String) {
            identifier++

            val channelId = "liveshopper_example"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = channelId
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel(channelId, name, importance).apply {
                    description = channelId
                }

                val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }

            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(com.liveshopper.example.R.drawable.ic_notification)
                .setStyle(NotificationCompat.BigTextStyle().bigText(body))
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_MAX)

            with(NotificationManagerCompat.from(context)) {
                notify(identifier % 20, builder.build())
            }
        }
    }
}
