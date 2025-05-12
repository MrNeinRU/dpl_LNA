package ru.malygin.anytoany.data.wifi_analyser

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.util.Log
import ru.malygin.anytoany.data.events.getNetStatePlatform


class WifiManager(
    val context: Context
) {

    val wifiManager: WifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
    val intentFilter = IntentFilter()

    val wifiScanReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val success = intent!!.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
            if (success){
                val data = scanSuccess()
//                Log.i(nameForLog(),"scan success => $data")
            }
        }
    }

    fun addReceiver() {
        try {
            intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
            context.registerReceiver(wifiScanReceiver, intentFilter)
        }catch (e: Exception) {
            Log.i(nameForLog(),"addReceiver failed =>", e)
        }
    }

    fun startScan() {
        try {
            val success = wifiManager.startScan()
        }catch (e: Exception) {
            Log.i(nameForLog(),"Wifi scan start => failed")
        }
    }


    @SuppressLint("MissingPermission")
    fun scanSuccess(): List<ScanResult> {
        val results: List<ScanResult> = wifiManager.scanResults

        return results
    }

    private fun nameForLog(): String {
        return this.toString()
    }
}