package ru.malygin.anytoany.data.events

import android.net.wifi.ScanResult
import android.net.wifi.WifiInfo
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.flowOf
import ru.malygin.anytoany.data.dtos.PDFDto
import ru.malygin.anytoany.data.dtos.WifiNetworkDto
import ru.malygin.anytoany.data.wifi_analyser.WifiManager

//actual fun createPDFile(pdfDto: PDFDto) {
//}

@Composable
actual fun getNetStatePlatform(): List<WifiNetworkDto> {
    val ctx = LocalContext.current
    val wifiManager = WifiManager(ctx)
    //todo проверка получения разрешений
    if (true){
        wifiManager.addReceiver()
        wifiManager.startScan()
    }

    val res = wifiManager.scanSuccess()

    Log.i("::S", "$res")

    return res.map {scResult ->

        WifiNetworkDto(
            ssid = scResult.SSID,
            channelPeak = wifiManager.wifiManager.calculateSignalLevel(scResult.level),
            channels = listOf(0),
            channelWidth = scResult.channelWidth,
            signalLevel = scResult.level.toFloat(),
            mac = scResult.BSSID,
            timeState = scResult.timestamp.toString(),
            vendor = null,
            frequency = scResult.frequency.toString(),
            capabilities = scResult.capabilities,
            locked = scResult.isPasspointNetwork,
        )
    }
}



actual fun Modifier.getScrollEvent(
    eventType: PointerEventType?,
    onEvent: (scroll: Double) -> Unit
): Modifier {
    return this
}