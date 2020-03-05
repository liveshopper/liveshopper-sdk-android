package com.liveshopper.example.utils

import android.content.Context
import android.provider.Settings
import com.liveshopper.sdk.LiveShopper
import com.liveshopper.sdk.model.LSEvent

fun fromEvent(event: LSEvent): String {
    val confidence = fromEventConfidence(event.confidence)

    return when (event.type) {
        LSEvent.LSEventType.USER_ENTERED_GEOFENCE -> "entered geofence ${event.geofence?.description} with $confidence"
        LSEvent.LSEventType.USER_EXITED_GEOFENCE -> "exited geofence ${event.geofence?.description} with $confidence"
        LSEvent.LSEventType.USER_ENTERED_PLACE -> "entered place ${event.place?.name} with $confidence"
        LSEvent.LSEventType.USER_EXITED_PLACE -> "exited place ${event.place?.name} with $confidence"
        LSEvent.LSEventType.USER_STARTED_TRAVELING -> "started traveling with $confidence"
        LSEvent.LSEventType.USER_STOPPED_TRAVELING -> "stopped traveling with $confidence"
        else -> "Unknown"
    }
}

fun fromEventConfidence(confidence: LSEvent.LSEventConfidence): String {
    return when (confidence) {
        LSEvent.LSEventConfidence.LOW -> "low confidence"
        LSEvent.LSEventConfidence.MEDIUM -> "medium confidence"
        LSEvent.LSEventConfidence.HIGH -> "high confidence"
        else -> "unknown confidence"
    }
}

fun fromLocationSource(source: LiveShopper.LSLocationSource): String {
    return when (source) {
        LiveShopper.LSLocationSource.FOREGROUND_LOCATION -> "foreground location"
        LiveShopper.LSLocationSource.BACKGROUND_LOCATION -> "background location"
        LiveShopper.LSLocationSource.MANUAL_LOCATION -> "manual location"
        LiveShopper.LSLocationSource.GEOFENCE_ENTER -> "geofence enter"
        LiveShopper.LSLocationSource.GEOFENCE_DWELL -> "geofence dwell"
        LiveShopper.LSLocationSource.GEOFENCE_EXIT -> "geofence exit"
        else -> "unknown"
    }
}

fun fromStatus(status: LiveShopper.LSStatus): String {
    return when (status) {
        LiveShopper.LSStatus.SUCCESS -> "Success"
        LiveShopper.LSStatus.ERROR_LOCATION -> "Location Error"
        LiveShopper.LSStatus.ERROR_NETWORK -> "Network Error"
        LiveShopper.LSStatus.ERROR_PERMISSIONS -> "Permissions Error"
        LiveShopper.LSStatus.ERROR_PUBLISHABLE_KEY -> "Publishable Key Error"
        LiveShopper.LSStatus.ERROR_RATE_LIMIT -> "Rate Limit Error"
        LiveShopper.LSStatus.ERROR_SERVER -> "Server Error"
        LiveShopper.LSStatus.ERROR_UNAUTHORIZED -> "Unauthorized Error"
        else -> "Unknown Error"
    }
}

fun getUserId(context: Context?): String {
    if (context == null) {
        throw Error("Invalid applcation context!")
    }

    return Settings.Secure.getString(
        context.getContentResolver(),
        Settings.Secure.ANDROID_ID
    )
}
