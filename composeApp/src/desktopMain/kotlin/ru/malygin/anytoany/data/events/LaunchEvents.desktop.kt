package ru.malygin.anytoany.data.events

import androidx.compose.runtime.Composable
import ru.malygin.anytoany.data.dtos.PDFDto
import ru.malygin.anytoany.data.dtos.WifiNetworkDto
import ru.malygin.anytoany.data.pdf.PDFWorker
import java.io.BufferedReader
import java.io.InputStreamReader

actual fun createPDFile(pdfDto: PDFDto) =
    PDFWorker(pdfDto).createPDF()

@Composable
actual fun getNetStatePlatform(): List<WifiNetworkDto> {

//    val os = System.getProperty("os.name").lowercase()//todo kall
//    println(os)
//
//    val prs = when{
//        os.contains("win") -> {
//            ProcessBuilder("netsh", "wlan", "show", "networks", "mode=Bssid")
//        }
//        else -> {
//            ProcessBuilder("iwlist", "wlan0", "scan")
//        }
//    }.start()
//
//    val reader = BufferedReader(InputStreamReader(prs.inputStream))
//    val lines = reader.readLines()

    val ll = (1..22).map {
        WifiNetworkDto(
            ssid = "custom_$it"
        )
    }

    return ll
}