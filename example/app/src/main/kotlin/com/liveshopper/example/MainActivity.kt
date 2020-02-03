package com.liveshopper.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

// import com.liveshopper.sdk.LiveShopper

class MainActivity : AppCompatActivity() {
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // var publishableKey = "TFNGQUtF--LlYDpy2UrzPcj_rzFPl"
        // LiveShopper.initialize(publishableKey)

        // var userId = Utils.getUserId(this)
        // LiveShopper.setUserId(userId)

        // if (ContextCompat.checkSelfPermission(
        //         this,
        //         Manifest.permission.ACCESS_FINE_LOCATION
        //     ) != PackageManager.PERMISSION_GRANTED
        // ) {
        //     requestPermissions(
        //         arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
        //         PERMISSION_REQUEST_CODE
        //     )
        // }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
    }
}