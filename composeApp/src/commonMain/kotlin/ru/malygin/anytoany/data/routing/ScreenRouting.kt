package ru.malygin.anytoany.data.routing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.malygin.anytoany.data.adapters.AuthorizationRequestState
import ru.malygin.anytoany.data.dateTime.getTodayDate
import ru.malygin.anytoany.data.view_models.LocalNetworkAnalyseModel
import ru.malygin.anytoany.data.view_models.LoginModel
import ru.malygin.anytoany.ui.screens.MainScreen
import ru.malygin.anytoany.ui.screens.dplWrk2LocalNetworkAnalise
import ru.malygin.anytoany.ui.screens.dplWrkScreen
import ru.malygin.anytoany.ui.screens.login_screen.LoginMainScreen

interface ScreenRouting : Screen{
    val title: String
        get() = this.key.split(".").last()

    val titleRu:String
}

class HomeScreen: ScreenRouting{
    override val titleRu: String
        get() = "Главный экран"

    @Composable
    override fun Content() {
        MainScreen.mainScreen()
    }
}

class LoginScreen: ScreenRouting{
    override val titleRu: String
        get() = "Авторизация"

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { LoginModel() }
        val navigator = LocalNavigator.currentOrThrow

        when(screenModel.loginState.collectAsState().value){
            is AuthorizationRequestState.Success -> {
                navigator.replace(HomeScreen())
            }
            else -> Unit
        }

        LoginMainScreen(screenModel).loginScreen()
    }
}

class DplWrkScreen: ScreenRouting{
    override val titleRu: String
        get() = "ДПЛ"

    @Composable
    override fun Content() {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            dplWrkScreen()
        }
    }
}

class DplWrk2LocalNetworkAnalise: ScreenRouting{
    override val titleRu: String
        get() = "Анализ локальной сети"

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { LocalNetworkAnalyseModel() }

        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            dplWrk2LocalNetworkAnalise(screenModel)
        }
    }
}

fun Screen.getScreenRoute(): ScreenRouting = this as ScreenRouting