@file:Suppress("ClassName")
package ru.malygin.anytoany.data.adapters

import kotlinx.coroutines.delay
import ru.malygin.anytoany.data.constants.__Fake__UserInfo
import ru.malygin.anytoany.data.constants.__Fake__database_cns
import ru.malygin.anytoany.data.data_cls.UserInformation
import ru.malygin.anytoany.data.dtos.NetworkClustDto
import ru.malygin.anytoany.data.utils.FacilityUtils


/**
 * сетевое взаимодействие
 */
class NetworkingAdapter {
    /**
     * если будет серверное взаимодействие, то заменить NetworkClustDto на NetworkingState_GetFacilityCluster для дальнейшей обработки
     */
    suspend fun getFacilityCluster(token: String?):NetworkingState_GetFacilityCluster?{
        if (token == null) return null
        // имитация задержки сетевого взаимодействия
        delay(1000)//4000

        //val receivedData: String = "{type: 'success', data: '...'}"
        //=>
        //val receivedDataGet = "receivedData.fromJson() => NetworkingState_GetFacilityCluster"
        //=> передовать это дальше для визуального отображения данных или ошибки

        val rre = NetworkingState_GetFacilityCluster.Success(
            data = GetFacilityCluster.getData()
        )

        return rre
    }

    suspend fun getUserInformation(token: String = "123"): UserInformation{
        delay(1000)

        val rre = __Fake__UserInfo.IT_ADMIN

        return rre
    }

    suspend fun authorizationRequest(
        login: String,
        password: String
    ): AuthorizationRequestState{
        delay(1000)

        //request
        //response
        //
        //test_sample
        return when{
            login == __Fake__database_cns.NET_USER_IDENTIFIER && password == __Fake__database_cns.NET_USER_PASSWORD -> {
                AuthorizationRequestState.Success(
                    token = "123"
                )
            }
            else -> {
                AuthorizationRequestState.Error(
                    message = "Неверный логин или пароль"
                )
            }
        }
    }
}

sealed class NetworkingState_GetFacilityCluster {
    data class Success(
        val data: NetworkClustDto
    ): NetworkingState_GetFacilityCluster()

    data class Error(
        val message: String
    ): NetworkingState_GetFacilityCluster()
}

// получение данных о предприятие через сервисный API (ложные данные)
private object GetFacilityCluster{
    fun getData(): NetworkClustDto = FacilityUtils.generateFakeFacilitiesNetworkClustDto()
}

sealed class AuthorizationRequestState{
    data object Awaiting: AuthorizationRequestState()

    data object Processing: AuthorizationRequestState()

    data class Success(
        val token: String
    ): AuthorizationRequestState()

    data class Error(
        val message: String
    ): AuthorizationRequestState()
}