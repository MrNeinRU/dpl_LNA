package ru.malygin.anytoany.data.dtos

import ru.malygin.anytoany.data.utils.WifiAnalyze


data class WifiNetworkDto(
    val ssid: String = "null",
    val channelPeak: Int = -1,
    val channels: List<Int> = listOf(0),
    val signalLevel: Float = -100f,//Received Signal Strength Indicator [dBm (русское дБм) — Децибел

    val mac: String = "00:00:null:00:00",//bssid
    val timeState: String = "0",
    /**
     * тех параметр
     */
    val channelWidth: Int = -1,

    val vendor: String? = null,
    val frequency: String = "-1",//частота работы точки Wi-Fi [Гц]
    val capabilities: String? = null,//дополнительные возможности точки Wi-Fi
    val locked: Boolean = false
){
    val channelPeakByFrequency = WifiAnalyze.getChannelByFrequency(frequency.toIntOrNull()?:-1)
    val signalLevelByLevel = WifiAnalyze.signalLevelBydBm(signalLevel)
    val ghzByFrequency = WifiAnalyze.ghzByFrequency(frequency.toIntOrNull()?:-1)
    val ghzByFrequencyNormal = ghzByFrequency.normalName
    val realChannelWidth = WifiAnalyze.getRange(channelWidth)
}