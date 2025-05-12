package ru.malygin.anytoany.data.utils

import ru.malygin.anytoany.data.constants.WifiConst


object WifiAnalyze {

    fun getChannelByFrequency(frequency: Int): Int {
        return when (frequency) {
            in 2412..2484 -> (frequency - 2412) / 5 + 1
            in 5170..5825 -> (frequency - 5170) / 5 + 34
            else -> -1
        }
    }

    fun signalLevelBydBm(dbm: Float): Float {
        return when{
            dbm >= 90 -> 9f
            dbm >= 0 -> 0.5f
            else -> -1f*dbm/10f
        }
    }

    fun ghzByFrequency(frequency: Int): WifiConst.WiFiGHZ {
        return when{
            frequency >= 5955 -> WifiConst.WiFiGHZ._6ghz
            frequency >= 2484 -> WifiConst.WiFiGHZ._5ghz
            else -> WifiConst.WiFiGHZ._2ghz
        }
    }

    /**
     * Get range of WiFi channel width
     * @param channelWidth Int pass value like ScanResult.CHANNEL_WIDTH_20MHZ
     */
    fun getRange(channelWidth: Int): Int {
        return when(channelWidth){
            WifiConst.ChannelWidth_.CHANNEL_WIDTH_20MHZ.androidId -> WifiConst.ChannelWidth_.CHANNEL_WIDTH_20MHZ.realValue
            WifiConst.ChannelWidth_.CHANNEL_WIDTH_40MHZ.androidId -> WifiConst.ChannelWidth_.CHANNEL_WIDTH_40MHZ.realValue
            WifiConst.ChannelWidth_.CHANNEL_WIDTH_80MHZ.androidId,
                 WifiConst.ChannelWidth_.CHANNEL_WIDTH_80MHZ_PLUS_MHZ.androidId-> WifiConst.ChannelWidth_.CHANNEL_WIDTH_80MHZ.realValue
            WifiConst.ChannelWidth_.CHANNEL_WIDTH_160MHZ.androidId -> WifiConst.ChannelWidth_.CHANNEL_WIDTH_160MHZ.realValue
            WifiConst.ChannelWidth_.CHANNEL_WIDTH_320MHZ.androidId -> WifiConst.ChannelWidth_.CHANNEL_WIDTH_320MHZ.realValue
            else -> -1
        }
    }
}