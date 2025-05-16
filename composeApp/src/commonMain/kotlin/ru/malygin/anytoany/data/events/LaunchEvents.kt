package ru.malygin.anytoany.data.events

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.AwaitPointerEventScope
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import ru.malygin.anytoany.data.dtos.PDFDto
import ru.malygin.anytoany.data.dtos.WifiNetworkDto

//expect fun createPDFile(pdfDto: PDFDto = PDFDto())

@Composable
expect fun getNetStatePlatform(): List<WifiNetworkDto>

expect fun Modifier.getScrollEvent(
    eventType: PointerEventType? = null,
    onEvent: (scroll: Double) -> Unit
): Modifier