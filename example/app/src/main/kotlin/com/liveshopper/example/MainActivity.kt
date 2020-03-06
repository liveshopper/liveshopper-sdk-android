package com.liveshopper.example

import android.location.Location
import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.liveshopper.sdk.LiveShopper
import com.liveshopper.sdk.LiveShopperTrackingOptions
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(com.liveshopper.example.R.layout.activity_main)
        setPermissions()

        LiveShopper.initialize("TFNGQUtF--LlYDpy2UrzPcj_rzFPl") // replace with your publishable key
        LiveShopper.setLogLevel(LiveShopper.LSLogLevel.DEBUG)

        LiveShopper.Tracking.once() { status, location, events, user ->
            Timber.tag("EXAMPLE").v("track once | status = ${status}; location = $location; events = $events; user = $user")
        }

        val options = LiveShopperTrackingOptions.RESPONSIVE
        options.sync = LiveShopperTrackingOptions.LSTrackingOptionsSync.ALL
        LiveShopper.Tracking.start(options)

        var origin = Location("example")
        origin.latitude = 41.0600898
        origin.longitude = -83.6790902

        LiveShopper.Tasks.search(origin, 5000.0, 0.0) { status, location, tasks ->
            Timber.tag("example").d("task search | status = $status; location = $location; count = ${tasks?.count()}")

            if (tasks != null) {
                for (task in tasks) {
                    Timber.tag("example").d("task | title = ${task.title}; description = ${task.description}")
                }
            }
        }

        LiveShopper.Places.search(origin, 50000.0, 0.0) { status, location, places ->
            Timber.tag("example").d("place search | status = $status; location = $location; count = ${places?.count()}")

            if (places != null) {
                for (place in places) {
                    Timber.tag("example").d("place | name = ${place.name}; address = ${place.address?.formattedAddress}; lat = ${place.location.latitude}; lng = ${place.location.longitude};")
                }
            }
        }
    }

    private fun setPermissions() {
        when {
            Build.VERSION.SDK_INT >= 29 -> ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                PERMISSION_REQUEST_CODE
            )
            Build.VERSION.SDK_INT in 23..28 -> ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
    }
}
