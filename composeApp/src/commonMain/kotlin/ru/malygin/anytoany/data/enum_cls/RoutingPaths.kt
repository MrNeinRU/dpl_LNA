package ru.malygin.anytoany.data.enum_cls

import ru.malygin.anytoany.data.routing.DplWrk2LocalNetworkAnalise
import ru.malygin.anytoany.data.routing.ScreenRouting

enum class RoutingPaths(
    val nameOfScreen: String,
    val screen: ScreenRouting?,
    val isUsed: Boolean = true
) {
    LOCAL_NETWORK_ANALYSE(
        nameOfScreen = "Анализ локальной сети",
        screen = DplWrk2LocalNetworkAnalise()
    ),
    FAKE_APPEALS(
        nameOfScreen = "Обращения",
        screen = null,
        isUsed = false
    ),
    FAKE_TASKS(
        nameOfScreen = "Задачи",
        screen = null,
        isUsed = false
    )
}