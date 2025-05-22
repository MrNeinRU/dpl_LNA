@file:OptIn(ExperimentalUuidApi::class)
@file:Suppress("ClassName", "PropertyName")

package ru.malygin.anytoany.data.dtos

import androidx.annotation.IntRange
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.malygin.anytoany.data.data_cls.Product_Version_Save
import ru.malygin.anytoany.data.utils.FacilityUtils
import ru.malygin.anytoany.data.utils.FacilityUtils.getNormalizedNameOfFacility
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * DTO для представления сетевой информации о локальной сети предприятия
 */
@Serializable
@SerialName("network_clust_dto")
data class NetworkClustDto(
    @IntRange(from = -1)
    val netId: Int = -1,
    val timeStamp: Long = 0,
    val devices: List<FacilityDeviceHeader>
){
    val normalizedName: String = getNormalizedNameOfFacility(netId)
}

@Serializable
@SerialName("facility_device_header")
data class FacilityDeviceHeader(
    val dv_id: Uuid,
    val isTrusted: Boolean = false,
    val isActive: Boolean = true,
    val dv_os: Product_Version_Save,
    val dv_programs: List<Product_Version_Save>? = null,
    val dv_info: FacilityDevice
)

@Serializable
@SerialName("facility_device")
sealed class FacilityDevice(
    val realConnection: List<FacilityDeviceConnections>, // список физических соединений
    val virtualConnection: List<FacilityDeviceConnections>?, // список виртуальных соединений (куда может подключиться устройство) (доступность)
){
    @Serializable
    @SerialName("pc")
    data class PC(
        val _networkInfo: NetworkDeviceInfo.PC,
        val _realConnection: List<FacilityDeviceConnections>,
        val _virtualConnection: List<FacilityDeviceConnections>?,
    ) : FacilityDevice(_realConnection, _virtualConnection)

    //маршрутизатор
    @Serializable
    @SerialName("router")
    data class Router(
        val _networkInfo: NetworkDeviceInfo.Router,
        val _realConnection: List<FacilityDeviceConnections>,
        val _virtualConnection: List<FacilityDeviceConnections>?,
    ) : FacilityDevice(_realConnection, _virtualConnection)

    //коммутатор
    @Serializable
    @SerialName("switch")
    data class Switch(
        val _networkInfo: NetworkDeviceInfo.Switch,
        val _realConnection: List<FacilityDeviceConnections>,
        val _virtualConnection: List<FacilityDeviceConnections>?,
    ) : FacilityDevice(_realConnection, _virtualConnection)

    @Serializable
    @SerialName("server")
    data class Server(
        val _networkInfo: NetworkDeviceInfo.Server,
        val _realConnection: List<FacilityDeviceConnections>,
        val _virtualConnection: List<FacilityDeviceConnections>?,
    ) : FacilityDevice(_realConnection, _virtualConnection)

    //станок
    @Serializable
    @SerialName("machine")
    data class Machine(
        val _networkInfo: NetworkDeviceInfo.Machine,
        val _realConnection: List<FacilityDeviceConnections>,
        val _virtualConnection: List<FacilityDeviceConnections>?,
    )  : FacilityDevice(_realConnection, _virtualConnection)

    @Serializable
    @SerialName("unknown_device")
    data class UnknownDevice(
        val _networkInfo: NetworkDeviceInfo.UnknownDevice,
        val _realConnection: List<FacilityDeviceConnections>,
        val _virtualConnection: List<FacilityDeviceConnections>?,
    ): FacilityDevice(_realConnection, _virtualConnection)

    fun getUiName(): String {
        return when (this) {
            is Machine -> "Станок"
            is PC -> "Компьютер"
            is Router -> "Маршрутизатор"
            is Server -> "Сервер"
            is Switch -> "Коммутатор"
            is UnknownDevice -> "Неизвестное устройство"
        }
    }

    fun getUiNetInfo(): NetworkDeviceInfo {
        return when (this) {
            is Machine -> this._networkInfo
            is PC -> this._networkInfo
            is Router -> this._networkInfo
            is Server -> this._networkInfo
            is Switch -> this._networkInfo
            is UnknownDevice -> this._networkInfo
        }
    }


    fun getUiRealConnections(): List<FacilityDeviceConnections> {
        return when (this) {
            is Machine -> this._realConnection
            is PC -> this._realConnection
            is Router -> this._realConnection
            is Server -> this._realConnection
            is Switch -> this._realConnection
            is UnknownDevice -> this._realConnection
        }
    }

    fun getUiVirtualConnections(): List<FacilityDeviceConnections>? {
        return when (this) {
            is Machine -> this._virtualConnection
            is PC -> this._virtualConnection
            is Router -> this._virtualConnection
            is Server -> this._virtualConnection
            is Switch -> this._virtualConnection
            is UnknownDevice -> this._virtualConnection
        }
    }

}

@Serializable
@SerialName("network_device_info")
sealed class NetworkDeviceInfo(
    val _mac: String,
){
    @Serializable
    @SerialName("pc")
    data class PC(
        val ip: IpData,
        val mac: String,
    ): NetworkDeviceInfo(
        mac
    )

    @Serializable
    @SerialName("router")
    data class Router(
        val ip: List<IpData>,
        val mac: String,
    ) : NetworkDeviceInfo(
        mac
    )

    @Serializable
    @SerialName("switch")
    data class Switch(
        val ip: List<IpData>,
        val mac: String,
    ) : NetworkDeviceInfo(
        mac
    )

    @Serializable
    @SerialName("server")
    data class Server(
        val ip: List<IpData>,
        val mac: String,
    ) : NetworkDeviceInfo(
        mac
    )

    @Serializable
    @SerialName("machine")
    data class Machine(
        val ip: IpData,
        val mac: String,
    ) : NetworkDeviceInfo(
        mac
    )

    @Serializable
    @SerialName("unknown_device")
    data class UnknownDevice(
        val ip: IpData,
        val mac: String,
    ) : NetworkDeviceInfo(
        mac
    )

    fun getUiMAC(): String {
        return when (this) {
            is Machine -> this.mac
            is PC -> this.mac
            is Router -> this.mac
            is Server -> this.mac
            is Switch -> this.mac
            is UnknownDevice -> this.mac
        }
    }

    fun getUiIPs(): List<IpData> {
        return when (this) {
            is Machine -> listOf(this.ip)
            is PC -> listOf(this.ip)
            is Router -> this.ip
            is Server -> this.ip
            is Switch -> this.ip
            is UnknownDevice -> listOf(this.ip)
        }
    }
}

//dataclass

@Serializable
@SerialName("facility_device_connections")
data class FacilityDeviceConnections(
    val _device: String,//FacilityDevice,
    val _connectIn: FacilityDeviceRealConnectionPath//String //IP адрес подключения
)

@Serializable
@SerialName("facility_device_real_connection_path")
data class FacilityDeviceRealConnectionPath(
    val from: String, //от этого ip
    val to: String //до этого ip
)

@Serializable
@SerialName("ip_data")
data class IpData(
    val ip: String,
    val allowedPorts: List<Int>
)

//functional

fun List<FacilityDeviceHeader>.getDeviceById(dvId: Uuid): FacilityDeviceHeader? = this.find { fdh->
    fdh.dv_id == dvId
}
fun List<FacilityDeviceHeader>.getDeviceById(dvId: String): FacilityDeviceHeader? = this.find { fdh->
    fdh.dv_id.toString() == dvId
}

fun String.getNetworkClustDto(): NetworkClustDto? {
    //
    //преобразование JSON в DTO
    //
    //
    return FacilityUtils.generateFakeFacilitiesNetworkClustDto()
}