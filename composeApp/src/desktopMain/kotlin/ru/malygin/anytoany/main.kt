package ru.malygin.anytoany

import androidx.compose.ui.Alignment
import androidx.compose.ui.window.*
import ru.malygin.anytoany.data.local_database.createTokenDatabase
import ru.malygin.anytoany.data.local_database.getRoomDatabase

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
//        val generateDatabase = getRoomDatabase(createTokenDatabase())todo fix error
        App()
    }
}