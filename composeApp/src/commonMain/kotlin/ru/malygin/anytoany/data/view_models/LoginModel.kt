package ru.malygin.anytoany.data.view_models

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.malygin.anytoany.data.adapters.AuthorizationRequestState
import ru.malygin.anytoany.data.adapters.NetworkingAdapter

class LoginModel(
    private val na: NetworkingAdapter = NetworkingAdapter()
): ScreenModel {

    private val _loginState = MutableStateFlow<AuthorizationRequestState>(AuthorizationRequestState.Awaiting)
    val loginState = _loginState.asStateFlow()


    fun onLogin(
        login: String,
        password: String
    ) {
        screenModelScope.launch {
            _loginState.value = AuthorizationRequestState.Processing
            val ext = na.authorizationRequest(login, password)
            _loginState.value = ext
        }
    }

    override fun onDispose() {

    }
}