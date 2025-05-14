@file:Suppress("ClassName")
package ru.malygin.anytoany.data.constants

import ru.malygin.anytoany.data.data_cls.Product_Version_Save
import ru.malygin.anytoany.data.enum_cls.OsType

object __Fake__OsVer {

    fun windowsOs(
        version:String = "Microsoft Windows [Version 10.0.26100.3775]"
    ) = Product_Version_Save(
        productName = getOsName(OsType.WINDOWS),
        productVersion = version
    )

    fun windowsServerOs(
        version:String = "Microsoft Windows Server 2019 [Version 10.0.17763]"
    )= Product_Version_Save(
        productName = getOsName(OsType.WINDOWS_SERVER),
        productVersion = version
    )

    fun linuxOs(
        version:String = "OpenWrt 24.10.1"//OpenWrt 22.03.6 <= есть уязвимости (можно использовать для примера)
    ) = Product_Version_Save(
        productName = getOsName(OsType.LINUX),
        productVersion = version
    )

    fun androidOs(
        version:String = "Android 13"
    ) = Product_Version_Save(
        productName = getOsName(OsType.ANDROID),
        productVersion = version
    )

    fun unKnownOs(
        version:String = "Unknown"
    ) = Product_Version_Save(
        productName = getOsName(OsType.UNKNOWN),
        productVersion = version
    )


    private fun getOsName(osType: OsType): String = osType.osType
}