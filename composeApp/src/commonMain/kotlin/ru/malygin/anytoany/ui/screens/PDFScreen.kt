package ru.malygin.anytoany.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.malygin.anytoany.ui.platform.pdfViewerPlatform

@Composable
fun PdfScreen() {
    Box(
        modifier = Modifier
            .widthIn(0.dp, 1080.dp)
            .fillMaxWidth()
            .padding(
                horizontal = 24.dp,
                vertical = 16.dp
            ),
        contentAlignment = Alignment.TopCenter
    ){
        pdfViewerPlatform()
    }
}