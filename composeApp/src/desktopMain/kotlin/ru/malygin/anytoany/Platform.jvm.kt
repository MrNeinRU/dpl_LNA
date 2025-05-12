package ru.malygin.anytoany

import ru.malygin.anytoany.data.enum_cls.OsType

class JVMPlatform: Platform {
    override val name: String = "[Desktop] Java ${System.getProperty("java.version")}"
    override val osType: OsType =
        when {
            System.getProperty("os.name").lowercase().contains("windo") -> OsType.WINDOWS
            System.getProperty("os.name").lowercase().contains("linu") -> OsType.LINUX
            else -> OsType.UNKNOWN
        }
}

actual fun getPlatform(): Platform = JVMPlatform()