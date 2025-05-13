package ru.malygin.anytoany.data.view_models

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.malygin.anytoany.data.adapters.AuthorizationRequestState
import ru.malygin.anytoany.data.adapters.NetworkingAdapter
import ru.malygin.anytoany.data.constants.__Fake__database_DAO

class LoginModel(
    private val database: __Fake__database_DAO = __Fake__database_DAO ,
    private val na: NetworkingAdapter = NetworkingAdapter()
): ScreenModel {

    private val _loginState = MutableStateFlow<AuthorizationRequestState>(AuthorizationRequestState.Awaiting)
    val loginState = _loginState.asStateFlow()

    private val _needToLogin = MutableStateFlow<NeedToLoginState>(NeedToLoginState.Loading)
    val needToLogin = _needToLogin.asStateFlow()

     init{
        screenModelScope.launch {
            if (database.getStorageToken() == null)
                _needToLogin.emit(NeedToLoginState.NeedToLogin(true))

            if (na.checkRemoteToken(database.getStorageToken()!!))
                _needToLogin.emit(NeedToLoginState.NeedToLogin(false))
            else
                _needToLogin.emit(NeedToLoginState.NeedToLogin(true))
        }
    }


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

sealed class  NeedToLoginState{
    data object Loading: NeedToLoginState()

    //true - need to login
    data class NeedToLogin(val reason: Boolean): NeedToLoginState()
}