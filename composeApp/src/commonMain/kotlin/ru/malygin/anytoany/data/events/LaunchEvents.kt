package ru.malygin.anytoany.data.events

import androidx.compose.runtime.Composable
import ru.malygin.anytoany.data.dtos.PDFDto
import ru.malygin.anytoany.data.dtos.WifiNetworkDto

//expect fun createPDFile(pdfDto: PDFDto = PDFDto())

@Composable
expect fun getNetStatePlatform(): List<WifiNetworkDto>