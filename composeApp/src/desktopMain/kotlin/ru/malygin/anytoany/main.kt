package ru.malygin.anytoany

import androidx.compose.ui.Alignment
import androidx.compose.ui.window.*

fun main() = application {
    val state = rememberWindowState(
        placement = WindowPlacement.Floating,
        position = WindowPosition.Aligned(Alignment.Center)
    )

    Window(
        onCloseRequest = ::exitApplication,
        state = state,
        title = "AnyToAny",
    ) {
        App()
    }
}