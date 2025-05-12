package ru.malygin.anytoany.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.malygin.anytoany.data.dtos.WifiNetworkDto
import ru.malygin.anytoany.data.events.getNetStatePlatform
import ru.malygin.anytoany.getPlatform

@Composable
fun dplWrkScreen() {
    Box(
        modifier = Modifier
            .widthIn(0.dp, 1080.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ){

        val netState: List<WifiNetworkDto> = getNetStatePlatform()
            .sortedBy {
                it.signalLevelByLevel
            }

        Column(
            modifier = Modifier
        ) {

            LazyColumn(
                modifier = Modifier
            ) {
                items(netState){data ->
                    Text("SSID: ${data.ssid}")
                    Text("GHZ: ${data.ghzByFrequencyNormal}")
                    Text("MAC: ${data.mac}")
                    Text("timeStamp: ${data.timeState}")
                    Text("CAPABILITIES: ${data.capabilities}")
//                    Text("CHANNEL_PEAK: ${data.channelPeak}")
                    Text("CHANNEL_PEAK_BY_FREQ: ${data.channelPeakByFrequency}")
//                    Text("CHANNELS: ${data.channels}")
                    Text("FREQUENCY: ${data.frequency}")
                    Text("SIGNAL_LEVEL: ${data.signalLevel}")
//                    Text("SIGNAL_LEVEL_LEVEL: ${data.signalLevelByLevel}")
                    Text("CHANNELS_WIDTH: ${data.realChannelWidth} MHz")
                    Text("VENDOR: ${data.vendor}")
                    Text("LOCKED: ${data.locked}")
                    HorizontalDivider(
                        thickness = 3.dp
                    )
                }
            }
        }
    }
}