@file:Suppress("PropertyName")

package ru.malygin.anytoany.data.adapters

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import ru.malygin.anytoany.data.constants.Settings
import ru.malygin.anytoany.data.constants.toToken
import ru.malygin.anytoany.data.dtos.getNetworkClustDto
import ru.malygin.anytoany.data.enum_cls.NetQueryMethods
import ru.malygin.anytoany.data.exceptions.TokenIsNullExc
import ru.malygin.anytoany.data.exceptions.UnknownNetworkExc


private
class RealNetworkAdapter(
    token: String?
) {
    private val _token = token
    private val client = HttpClient(
        engineFactory = OkHttp
    )

    suspend fun getFacilityCluster(): NetworkingState_GetFacilityCluster{
        val pMethod = Settings.GetEntryByQuery(NetQueryMethods.GET_FACILITY_INFO)
        var exit: NetworkingState_GetFacilityCluster = NetworkingState_GetFacilityCluster.Error(UnknownNetworkExc())

        if (!checkToken())
            exit = NetworkingState_GetFacilityCluster.Error(TokenIsNullExc())

        val response = client.get{
            url{
                protocol = URLProtocol.HTTPS
                host = Settings.FACILITY_URL_LOCAL_HOST
            }
            parameters {
                append(pMethod.getFirst(), pMethod.getSecond())
            }
        }

        when(response.status.value){
            in 200..299 -> {
                when(val ex = response.bodyAsText().getNetworkClustDto()){
                    null ->
                        exit = NetworkingState_GetFacilityCluster.Error(UnknownNetworkExc())
                    else -> NetworkingState_GetFacilityCluster.Success(ex)
                }
            }
            else -> exit = NetworkingState_GetFacilityCluster.Error(UnknownNetworkExc())
        }

        return exit
    }

    suspend fun authorizationRequest(
        login: String,
        password: String
    ): AuthorizationRequestState{
        val aMethod = Settings.GetEntryByQuery(NetQueryMethods.AUTH)
        var exit: AuthorizationRequestState = AuthorizationRequestState.Processing

        val response = client.get{
            url{
                protocol = URLProtocol.HTTPS
                host = Settings.FACILITY_URL_LOCAL_HOST
            }
            parameters {
                append(aMethod.getFirst(), aMethod.getSecond())
                append("login", login)
                append("password", password)
            }
        }
        when(response.status.value){
            in 200..299 -> {
                when(val ex = response.bodyAsText().toToken()){
                    null ->
                        exit = AuthorizationRequestState.Error(TokenIsNullExc().message?:"Unknown error")
                    else -> AuthorizationRequestState.Success(ex)
                }
            }
            else -> exit = AuthorizationRequestState.Error(UnknownNetworkExc().message?:"Unknown error")
        }

        return exit
    }

    private fun checkToken(): Boolean =
        _token != null
}

suspend fun tr() = RealNetworkAdapter(null).getFacilityCluster()
suspend fun tr1() = RealNetworkAdapter(null).authorizationRequest("admin","admin")