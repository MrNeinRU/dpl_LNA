@file:Suppress("ClassName")
package ru.malygin.anytoany.data.utils

import ru.malygin.anytoany.data.constants.__Fake__OsVer
import ru.malygin.anytoany.data.constants.__Fake__ProgVer
import ru.malygin.anytoany.data.dtos.*
import ru.malygin.anytoany.data.dtos.FacilityDevice.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

object FacilityUtils {
    private const val FACILITIES_NONE = "Нет информации о предприятии"
    private val FACILITIES: Map<Int, String> = mapOf(   // !_! может переделать на получение списка через сервер
        -1 to FACILITIES_NONE,
        0 to "ООО \"Без филиала\"",
        1 to "ООО \"Мысль\"",
    )

    fun getNormalizedNameOfFacility(
        netId: Int
    ): String {
        return FACILITIES[netId] ?: FACILITIES_NONE
    }

    fun List<FacilityDeviceHeader>.getRealConnections(): List<FacilityDeviceConnections>{
        val fdList = mutableListOf<FacilityDeviceConnections>()

        this.map { ffD_->
            when (val ffD = ffD_.dv_info) {
                is Machine -> fdList.addAll(ffD._realConnection)
                is PC -> fdList.addAll(ffD._realConnection)
                is Router -> fdList.addAll(ffD._realConnection)
                is Server -> fdList.addAll(ffD._realConnection)
                is Switch -> fdList.addAll(ffD._realConnection)
                is UnknownDevice -> fdList.addAll(ffD._realConnection)
            }
        }
        return fdList
    }

    @OptIn(ExperimentalUuidApi::class)
    fun List<FacilityDeviceHeader>.getNameOfDevice(id:String?): String {
        this.forEach { ffD->TODO()
//            when (ffD) {
//                is Machine -> if (ffD._id.toString() == id) return ffD.getUiName() + " ${ffD._id}"
//                is PC -> if (ffD._id.toString() == id) return ffD.getUiName() + " ${ffD._id}"
//                is Router -> if (ffD._id.toString() == id) return ffD.getUiName() + " ${ffD._id}"
//                is Server -> if (ffD._id.toString() == id) return ffD.getUiName() + " ${ffD._id}"
//                is Switch -> if (ffD._id.toString() == id) return ffD.getUiName() + " ${ffD._id}"
//                is UnknownDevice -> if (ffD._id.toString() == id) return ffD.getUiName() + " ${ffD._id}"
//            }
        }
        return "Неизвестное устройство"
    }

    @OptIn(ExperimentalUuidApi::class)
    fun List<FacilityDeviceHeader>.getUnit(id: String?): FacilityDeviceHeader? {
        return this.find { ffD->
            ffD.dv_id.toString() == id
        }
    }

    private val dv = listOf(
        __S__.pc1,
        __S__.pc2,
        __S__.router1,
        __S__.switch1,
        __S__.router2,
        __S__.server1,
        __S__.machine1,
        __S__.unknownDevice1
    )

    fun getFacilityDeviceByIp(ip:String): FacilityDeviceHeader? = dv.find {
        when (val ll = it.dv_info) {
            is Machine -> ll._networkInfo.ip.ip == ip
            is PC -> ll._networkInfo.ip.ip == ip
            is Router -> ip in ll._networkInfo.ip.map { s-> s.ip }
            is Server -> ip in ll._networkInfo.ip.map { s-> s.ip }
            is Switch -> ip in ll._networkInfo.ip.map { s-> s.ip }
            is UnknownDevice -> ll._networkInfo.ip.ip == ip
        }
    }


    /**
     * Фейковый метод для генерации сетевого кластера
     *
     * >[FacilityDevice.PC]
     *
     * >[FacilityDevice.PC]
     *
     * >>[FacilityDevice.Router]
     *
     * >>>[FacilityDevice.Switch] <-- [FacilityDevice.UnknownDevice]
     *
     * >>[FacilityDevice.Router]
     *
     * >[FacilityDevice.Server]
     *
     * >[FacilityDevice.Machine]
     *
     * //> 2 пользователя подключены к роутеру, роутер к коммутатору, коммутатор к серверу через другой роутер и неизвестное устройство из внешней сети <\\
     *
     * mac ->
     *
     * > Switch = 00:00:00:00:00:01
     *
     * >> Router (в верхней сети) = 00:00:00:00:01:01
     *
     * >> Router (в нижней сети) = 00:00:00:00:02:01
     *
     * @see [NetworkClustDto]
     */
    fun generateFakeFacilitiesNetworkClustDto(): NetworkClustDto{

        return NetworkClustDto(
            netId = 1,
            timeStamp = 1223,
            devices = dv
        )
    }


    @OptIn(ExperimentalUuidApi::class)
    object __S__{
        val pc1 =
            FacilityDeviceHeader(
                dv_id = Uuid.random(),
                isTrusted = true,
                isActive = true,
                dv_os = __Fake__OsVer.windowsOs(),
                dv_programs = listOf(
                    __Fake__ProgVer.defaultProgram()
                ),
                dv_info =
                    PC(
                        _networkInfo = NetworkDeviceInfo.PC(
                            ip = IpData(
                                ip = __S__Const.PC_1_IP_192_168_1_10,
                                allowedPorts = listOf(22, 3389)
                            ),
                            mac = "00:00:00:00:01:10",
                        ),
                        _realConnection = listOf(
                            FacilityDeviceConnections(
                                _device = "router1",
                                _connectIn = FacilityDeviceRealConnectionPath(
                                    from = __S__Const.PC_1_IP_192_168_1_10,
                                    to = __S__Const.ROUTER_1_IP_192_168_1_1
                                )
                            )
                        ),
                        _virtualConnection = listOf(
                            FacilityDeviceConnections(
                                _device = "pc2",
                                _connectIn = FacilityDeviceRealConnectionPath(
                                    from = __S__Const.PC_1_IP_192_168_1_10,
                                    to = __S__Const.PC_2_IP_192_168_1_11
                                )
                            ),
                            FacilityDeviceConnections(
                                _device = "server1",
                                _connectIn = FacilityDeviceRealConnectionPath(
                                    from = __S__Const.PC_1_IP_192_168_1_10,
                                    to = __S__Const.SERVER_1_IP_192_168_0_10
                                )
                            )
                        ),
                    )
            )


        val pc2 =
            FacilityDeviceHeader(
                dv_id = Uuid.random(),
                isTrusted = true,
                isActive = false,
                dv_os = __Fake__OsVer.windowsOs(/*TODO()*/),
                dv_programs = null/*TODO()*/,
                dv_info =
                    PC(
                        _networkInfo = NetworkDeviceInfo.PC(
                            ip = IpData(
                                ip = __S__Const.PC_2_IP_192_168_1_11,
                                allowedPorts = listOf(22, 3389)
                            ),
                            mac = "00:00:00:00:01:11",
                        ),
                        _realConnection = listOf(
                            FacilityDeviceConnections(
                                _device = "router1",
                                _connectIn = FacilityDeviceRealConnectionPath(
                                    from = __S__Const.PC_2_IP_192_168_1_11,
                                    to = __S__Const.ROUTER_1_IP_192_168_1_1
                                )
                            )
                        ),
                        _virtualConnection = listOf(
                            FacilityDeviceConnections(
                                _device = "pc1",
                                _connectIn = FacilityDeviceRealConnectionPath(
                                    from = __S__Const.PC_2_IP_192_168_1_11,
                                    to = __S__Const.PC_1_IP_192_168_1_10
                                )
                            ),
                            FacilityDeviceConnections(
                                _device = "server1",
                                _connectIn = FacilityDeviceRealConnectionPath(
                                    from = __S__Const.PC_2_IP_192_168_1_11,
                                    to = __S__Const.SERVER_1_IP_192_168_0_10
                                )
                            )
                        ),
                    )
            )



        val router1 =
            FacilityDeviceHeader(
                dv_id = Uuid.random(),
                isTrusted = true,
                isActive = true,
                dv_os = __Fake__OsVer.linuxOs(/*TODO()*/),
                dv_programs = null,
                dv_info =
                    Router(
                        _networkInfo = NetworkDeviceInfo.Router(
                            ip = listOf(
                                IpData(
                                    ip = __S__Const.ROUTER_1_IP_192_168_1_1,
                                    allowedPorts = listOf(22)
                                ),
                                IpData(
                                    ip = __S__Const.ROUTER_1_IP_10_10_1_2,
                                    allowedPorts = listOf(22)
                                ),
                            ),
                            mac = "00:00:00:00:01:01",
                        ),
                        _realConnection = listOf(
                            FacilityDeviceConnections(
                                _device = "pc1",
                                _connectIn = FacilityDeviceRealConnectionPath(
                                    from = __S__Const.ROUTER_1_IP_192_168_1_1,
                                    to = __S__Const.PC_1_IP_192_168_1_10
                                )
                            ),
                            FacilityDeviceConnections(
                                _device = "pc2",
                                _connectIn = FacilityDeviceRealConnectionPath(
                                    from = __S__Const.ROUTER_1_IP_192_168_1_1,
                                    to = __S__Const.PC_2_IP_192_168_1_11
                                )
                            ),
                            FacilityDeviceConnections(
                                _device = "switch1",
                                _connectIn = FacilityDeviceRealConnectionPath(
                                    from = __S__Const.ROUTER_1_IP_10_10_1_2,
                                    to = __S__Const.SWITCH_1_IP_10_10_1_3
                                )
                            )
                        ),
                        _virtualConnection = null
                    )
            )


        val switch1 =
            FacilityDeviceHeader(
                dv_id = Uuid.random(),
                isTrusted = true,
                isActive = true,
                dv_os = __Fake__OsVer.linuxOs(/*TODO()*/),
                dv_programs = null,
                dv_info =
                    Switch(
                        _networkInfo = NetworkDeviceInfo.Switch(
                            ip = listOf(
                                IpData(
                                    ip = __S__Const.SWITCH_1_IP_10_10_1_3,
                                    allowedPorts = listOf(22)
                                ),// к верхнему роутеру \
                                IpData(
                                    ip = __S__Const.SWITCH_1_IP_10_10_1_1,
                                    allowedPorts = listOf(22)
                                ), // в интарнет          > может переделать в один 10.10.1.1
                                IpData(
                                    ip = __S__Const.SWITCH_1_IP_10_10_1_4,
                                    allowedPorts = listOf(22)
                                ), // к нижнему роутеру /
                            ),
                            mac = "00:00:00:00:00:01"
                        ),
                        _realConnection = listOf(
                            FacilityDeviceConnections(
                                _device = "router1",
                                _connectIn = FacilityDeviceRealConnectionPath(
                                    from = __S__Const.SWITCH_1_IP_10_10_1_3,
                                    to = __S__Const.ROUTER_1_IP_10_10_1_2
                                )
                            ),
                            FacilityDeviceConnections(
                                _device = "router2",
                                _connectIn = FacilityDeviceRealConnectionPath(
                                    from = __S__Const.SWITCH_1_IP_10_10_1_4,
                                    to = __S__Const.ROUTER_2_IP_10_10_1_5
                                )
                            ),
                            FacilityDeviceConnections(
                                _device = "unknown1",
                                _connectIn = FacilityDeviceRealConnectionPath(
                                    from = __S__Const.SWITCH_1_IP_10_10_1_1,
                                    to = __S__Const.UNKNOWN_DEVICE_IP_120_10_10_15
                                )
                            )
                        ),
                        _virtualConnection = null
                    )
            )


        val router2 =
            FacilityDeviceHeader(
                dv_id = Uuid.random(),
                isTrusted = true,
                isActive = true,
                dv_os = __Fake__OsVer.linuxOs(/*TODO()*/),
                dv_programs = null,
                dv_info =
                    Router(
                        _networkInfo = NetworkDeviceInfo.Router(
                            ip = listOf(
                                IpData(
                                    ip = __S__Const.ROUTER_2_IP_10_10_1_5,
                                    allowedPorts = listOf(22)
                                ),
                                IpData(
                                    ip = __S__Const.ROUTER_2_IP_192_168_0_1,
                                    allowedPorts = listOf(22)
                                ),
                            ),
                            mac = "00:00:00:00:02:01",
                        ),
                        _realConnection = listOf(
                            FacilityDeviceConnections(
                                _device = "switch1",
                                _connectIn = FacilityDeviceRealConnectionPath(
                                    from = __S__Const.ROUTER_2_IP_10_10_1_5,
                                    to = __S__Const.SWITCH_1_IP_10_10_1_4
                                )
                            ),
                            FacilityDeviceConnections(
                                _device = "server1",
                                _connectIn = FacilityDeviceRealConnectionPath(
                                    from = __S__Const.ROUTER_2_IP_192_168_0_1,
                                    to = __S__Const.SERVER_1_IP_192_168_0_10
                                )
                            ),
                            FacilityDeviceConnections(
                                _device = "machine1",
                                _connectIn = FacilityDeviceRealConnectionPath(
                                    from = __S__Const.ROUTER_2_IP_192_168_0_1,
                                    to = __S__Const.MACHINE_1_IP_192_168_0_20
                                )
                            )
                        ),
                        _virtualConnection = null
                    )
            )


        val server1 =
            FacilityDeviceHeader(
                dv_id = Uuid.random(),
                isTrusted = true,
                isActive = true,
                dv_os = __Fake__OsVer.windowsServerOs(),
                dv_programs = null,
                dv_info =
                    Server(
                        _networkInfo = NetworkDeviceInfo.Server(
                            ip = listOf(
                                IpData(
                                    ip = __S__Const.SERVER_1_IP_192_168_0_10,
                                    allowedPorts = listOf(20, 21, 22, 80)
                                ),
                            ),
                            mac = "00:00:00:00:02:02"
                        ),
                        _realConnection = listOf(
                            FacilityDeviceConnections(
                                _device = "router2",
                                _connectIn = FacilityDeviceRealConnectionPath(
                                    from = __S__Const.SERVER_1_IP_192_168_0_10,
                                    to = __S__Const.ROUTER_2_IP_192_168_0_1
                                )
                            )
                        ),
                        _virtualConnection = listOf(
                            FacilityDeviceConnections(
                                _device = "pc1",
                                _connectIn = FacilityDeviceRealConnectionPath(
                                    from = __S__Const.SERVER_1_IP_192_168_0_10,
                                    to = __S__Const.PC_1_IP_192_168_1_10
                                )
                            ),
                            FacilityDeviceConnections(
                                _device = "pc2",
                                _connectIn = FacilityDeviceRealConnectionPath(
                                    from = __S__Const.SERVER_1_IP_192_168_0_10,
                                    to = __S__Const.PC_2_IP_192_168_1_11
                                )
                            ),
                            FacilityDeviceConnections(
                                _device = "server1",
                                _connectIn = FacilityDeviceRealConnectionPath(
                                    from = __S__Const.SERVER_1_IP_192_168_0_10,
                                    to = __S__Const.MACHINE_1_IP_192_168_0_20
                                )
                            )
                        )
                    )
            )


        val machine1 =
            FacilityDeviceHeader(
                dv_id = Uuid.random(),
                isTrusted = true,
                isActive = true,
                dv_os = __Fake__OsVer.linuxOs(/*TODO()*/),
                dv_programs = null,
                dv_info =
                    Machine(
                        _networkInfo = NetworkDeviceInfo.Machine(
                            ip = IpData(
                                ip = __S__Const.MACHINE_1_IP_192_168_0_20,
                                allowedPorts = listOf(20, 21, 22)
                            ),
                            mac = "00:00:00:00:02:03"
                        ),
                        _realConnection = listOf(
                            FacilityDeviceConnections(
                                _device = "router2",
                                _connectIn = FacilityDeviceRealConnectionPath(
                                    from = __S__Const.MACHINE_1_IP_192_168_0_20,
                                    to = __S__Const.ROUTER_2_IP_192_168_0_1
                                )
                            )
                        ),
                        _virtualConnection = listOf(
                            FacilityDeviceConnections(
                                _device = "server1",
                                _connectIn = FacilityDeviceRealConnectionPath(
                                    from = __S__Const.MACHINE_1_IP_192_168_0_20,
                                    to = __S__Const.SERVER_1_IP_192_168_0_10
                                )
                            )
                        )
                    )
            )


        val unknownDevice1 =
            FacilityDeviceHeader(
                dv_id = Uuid.random(),
                isTrusted = false,
                isActive = true,
                dv_os = __Fake__OsVer.unKnownOs(),
                dv_programs = null,
                dv_info =
                    UnknownDevice(
                        _networkInfo = NetworkDeviceInfo.UnknownDevice(
                            ip = IpData(
                                ip = __S__Const.UNKNOWN_DEVICE_IP_120_10_10_15,
                                allowedPorts = listOf()
                            ),
                            mac = __S__Const.UNKNOWN_DEVICE_MAC_20_A0_22_0A_07_EF
                        ),
                        _realConnection = listOf(
                            FacilityDeviceConnections(
                                _device = "switch1",
                                _connectIn = FacilityDeviceRealConnectionPath(
                                    from = __S__Const.UNKNOWN_DEVICE_IP_120_10_10_15,
                                    to = __S__Const.SWITCH_1_IP_10_10_1_1
                                )
                            )
                        ),
                        _virtualConnection = null//_t!od!o_
                    )
            )

    }

    private object __S__Const{
        const val PC_1_IP_192_168_1_10 = "192.168.1.10"

        const val PC_2_IP_192_168_1_11 = "192.168.1.11"

        const val ROUTER_1_IP_192_168_1_1 = "192.168.1.1"
        const val ROUTER_1_IP_10_10_1_2 = "10.10.1.2"

        const val SWITCH_1_IP_10_10_1_3 = "10.10.1.3"
        const val SWITCH_1_IP_10_10_1_1 = "10.10.1.1"
        const val SWITCH_1_IP_10_10_1_4 = "10.10.1.4"

        const val ROUTER_2_IP_192_168_0_1 = "192.168.0.1"
        const val ROUTER_2_IP_10_10_1_5 = "10.10.1.5"

        const val MACHINE_1_IP_192_168_0_20 = "192.168.0.20"

        const val SERVER_1_IP_192_168_0_10 = "192.168.0.10"

        const val UNKNOWN_DEVICE_IP_120_10_10_15 = "120.10.10.15"
        const val UNKNOWN_DEVICE_MAC_20_A0_22_0A_07_EF = "20:a0:22:0a:07:ef"
    }
}