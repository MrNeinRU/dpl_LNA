package ru.malygin.anytoany.data.events

import androidx.compose.runtime.Composable
import ru.malygin.anytoany.data.dtos.PDFDto
import ru.malygin.anytoany.data.dtos.WifiNetworkDto

actual fun createPDFile(pdfDto: PDFDto) {
}

@Composable
actual fun getNetStatePlatform(): List<WifiNetworkDto> {

    val ll = (1..22).map {
        WifiNetworkDto(
            ssid = "custom_$it"
        )
    }

    return ll
}