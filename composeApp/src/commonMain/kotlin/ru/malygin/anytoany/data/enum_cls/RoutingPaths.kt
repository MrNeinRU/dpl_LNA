package ru.malygin.anytoany.data.enum_cls

import ru.malygin.anytoany.data.routing.DplWrk2LocalNetworkAnalise
import ru.malygin.anytoany.data.routing.ScreenRouting

enum class RoutingPaths(
    val nameOfScreen: String,
    val screen: ScreenRouting
) {
    LOCAL_NETWORK_ANALYSE(
        nameOfScreen = "Анализ локальной сети",
        screen = DplWrk2LocalNetworkAnalise()
    )
}