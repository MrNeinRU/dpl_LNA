package ru.malygin.anytoany

import ru.malygin.anytoany.data.enum_cls.OsType

interface Platform {
    val name: String
    val osType: OsType
}

expect fun getPlatform(): Platform