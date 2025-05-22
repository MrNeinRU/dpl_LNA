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
    val aro = 15.0
    val sle = 2000000.0
    val mf = (aro - 8.0) / aro
    val tco = 3100000.0

    fun dododo() = (aro*sle*mf-tco)/tco

    Window(
        onCloseRequest = ::exitApplication,
        state = state,
        title = "AnyToAny",
    ) {
//        val generateDatabase = getRoomDatabase(createTokenDatabase())todo fix error
        println(
            """
                mf = $mf
                fr1 = ${(aro*sle*mf-tco)}
                fr2 = $tco
                ROSI = ${dododo()}
            """.trimIndent()
        )
        App()
    }
}