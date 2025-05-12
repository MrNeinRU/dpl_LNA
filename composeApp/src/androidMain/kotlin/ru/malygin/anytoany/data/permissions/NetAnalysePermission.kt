package ru.malygin.anytoany.data.permissions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import androidx.core.app.ActivityCompat


object NetAnalysePermission{

    fun getPermission(
        activity: Activity
    ){
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CHANGE_WIFI_STATE),
            1
        )
        makeEnableLocationServices(activity.applicationContext)
    }

    /* включает экран включения службы по определению местоположения */
    private fun makeEnableLocationServices(context: Context) {

        // TODO: перед вызовом этой функции надо рассказать пользователю, зачем Вам доступ к местоположению
        val lm: LocationManager =
            context.applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val gpsEnabled: Boolean = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val networkEnabled: Boolean = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!gpsEnabled && !networkEnabled) {
            context.startActivity(Intent(ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }
}